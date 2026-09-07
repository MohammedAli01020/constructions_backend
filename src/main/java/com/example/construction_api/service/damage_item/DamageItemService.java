package com.example.construction_api.service.damage_item;

import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.DamageItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DamageItemService {
    DamageItem save(DamageItem damageItem);

    DamageItem findById(Long damageItemId);
    void deleteById(Long damageItemId);

    Page<DamageItem> getPageDamageItemsByDamage(Damage damage, Pageable pageable);
}
