package com.example.construction_api.service.damage_item;

import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.DamageItem;
import com.example.construction_api.repository.DamageItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DamageItemServiceImpl implements DamageItemService{
    private final DamageItemRepository damageItemRepository;

    public DamageItemServiceImpl(DamageItemRepository damageItemRepository) {
        this.damageItemRepository = damageItemRepository;
    }

    @Override
    public DamageItem save(DamageItem damageItem) {
        return damageItemRepository.save(damageItem);
    }

    @Override
    public DamageItem findById(Long damageItemId) {
        return damageItemRepository.findById(damageItemId).orElseThrow();
    }

    @Override
    public void deleteById(Long damageItemId) {
        damageItemRepository.deleteById(damageItemId);
    }

    @Override
    public Page<DamageItem> getPageDamageItemsByDamage(Damage damage, Pageable pageable) {
        return damageItemRepository.findAllByDamage(damage, pageable);
    }
}
