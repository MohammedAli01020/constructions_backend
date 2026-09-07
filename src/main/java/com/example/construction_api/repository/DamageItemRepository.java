package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.DamageItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageItemRepository extends JpaRepository<DamageItem, Long> {

    Page<DamageItem> findAllByDamage(Damage damage, Pageable pageable);
}
