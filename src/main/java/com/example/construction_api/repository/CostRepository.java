package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Cost;
import com.example.construction_api.model.persisitece.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CostRepository extends JpaRepository<Cost, Long> {
    @Transactional
    @Query("SELECT sum(cost.value) FROM Cost cost where cost.machine = :machine and cost.dateTime between :startDate and :endDate")
    Long sumCostsValueByMachineBetweenTwoDatesInMillis(@Param("machine") Machine machine,
                                                       @Param("startDate") Long startDate,
                                                       @Param("endDate") Long endDate);


    @Transactional
    @Query("SELECT sum(cost.value) FROM Cost cost where cost.dateTime between :startDate and :endDate")
    Long sumCostsValueBetweenTwoDatesInMillis(
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);
}
