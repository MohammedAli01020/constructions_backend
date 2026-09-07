package com.example.construction_api.repository;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface DamageRepository extends JpaRepository<Damage, Long> {
    @Transactional
    @Query("SELECT (SELECT sum(damageItem.cost) from  DamageItem damageItem where" +
            " damageItem.damage.damageId = damage.damageId) " +
            "FROM Damage damage where damage.damageId = :damageId")
    Long sumDamageCostsByDamageBetweenTwoDatesInMillis(@Param("damageId") Long damageId);


    @Transactional
    @Query("SELECT sum((SELECT sum(damageItem.cost) from  DamageItem damageItem where" +
            " damageItem.damage.machine = damage.machine)) " +
            "FROM Damage damage where damage.machine = :machine and damage.damageStatus = :damageStatus and damage.endDateTime between :startDate and :endDate")
    Long sumDamageCostsByMachineBetweenTwoDatesInMillis(@Param("machine") Machine machine,
                                                        @Param("damageStatus") DamageStatus damageStatus,
                                                        @Param("startDate") Long startDate,
                                                        @Param("endDate") Long endDate);

    @Transactional
    @Query("SELECT sum((SELECT sum(damageItem.cost) from  DamageItem damageItem where damageItem.damage.damageId = damage.damageId))" +
            "FROM Damage damage where damage.damageStatus = :damageStatus  and damage.endDateTime between :startDate and :endDate")
    Long sumDamageCostsBetweenTwoDatesInMillis(
            @Param("damageStatus") DamageStatus damageStatus,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);


    Long countAllByMachineAndDamageStatus(Machine machine, DamageStatus damageStatus);
}
