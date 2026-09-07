package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Transactional
    @Query("SELECT sum(purchase.cost) FROM Purchase purchase where purchase.dateTime between :startDate and :endDate")
    Long sumPurchaseCostsBetweenTwoDatesInMillis(
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);
}
