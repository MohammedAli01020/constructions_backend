package com.example.construction_api.controller;

import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.DamageItem;
import com.example.construction_api.model.requests.CreateDamageItemRequest;
import com.example.construction_api.model.requests.DamagePage;
import com.example.construction_api.service.damage.DamageService;
import com.example.construction_api.service.damage_item.DamageItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/damage_items/")
public class DamageItemController {

    private final DamageService damageService;
    private final DamageItemService damageItemService;


    public DamageItemController(DamageService damageService, DamageItemService damageItemService) {
        this.damageService = damageService;
        this.damageItemService = damageItemService;
    }

    @Transactional
    @PostMapping("modify")
    public ResponseEntity<DamageItem> modify(@Valid @RequestBody CreateDamageItemRequest createDamageItemRequest) {

        DamageItem damageItem = new DamageItem();

        if (createDamageItemRequest.getDamageItemId() != null) {
            damageItem = damageItemService.findById(createDamageItemRequest.getDamageItemId());
            if (damageItem == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Damage damage = damageService.findById(createDamageItemRequest.getDamageId());

        if (damage == null) {
            return ResponseEntity.notFound().build();
        }

        damageItem.setDamage(damage);
        damageItem.setCost(createDamageItemRequest.getCost());
        damageItem.setDescription(createDamageItemRequest.getDescription());
        damageItem.setInsertDateTime(createDamageItemRequest.getInsertDateTime());
        damageItem.setImageUrl(createDamageItemRequest.getImageUrl());


        return new ResponseEntity<>(damageItemService.save(damageItem), HttpStatus.OK);

    }


    @GetMapping("all")
    public ResponseEntity<Page<DamageItem>> getAllDamagesWithFilters(DamagePage damagePage, Long damageId) {

        Damage damage = damageService.findById(damageId);
        if (damage == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(damageItemService.
                getPageDamageItemsByDamage(damage, getPageable(damagePage)), HttpStatus.OK);
    }


    private Pageable getPageable(DamagePage damagePage) {

        Sort sort = Sort.by(damagePage.getSortDirection(), damagePage.getSortBy());

        return PageRequest.of(damagePage.getPageNumber(), damagePage.getPageSize(), sort);

    }

    @Transactional
    @DeleteMapping("delete/id/{damageItemId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long damageItemId) {
        damageItemService.deleteById(damageItemId);
    }

}
