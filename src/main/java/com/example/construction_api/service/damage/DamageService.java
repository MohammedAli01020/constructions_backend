package com.example.construction_api.service.damage;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.DamageCriteria;
import com.example.construction_api.model.requests.DamagePage;
import org.springframework.data.domain.Page;

public interface DamageService {
    Damage save(Damage damage);

    Damage findById(Long damageId);

    Page<Damage> getAllWithFilters(DamagePage damagePage, DamageCriteria damageCriteria);

    void deleteById(Long damageId);


    Long countAllByMachineAndDamageStatus(Machine machine, DamageStatus damageStatus);


    Long sumDamageCostsBetweenTwoDatesInMillis(DamageStatus damageStatus, Long startDate, Long endDate);

    Long sumDamageCostsByDamageBetweenTwoDatesInMillis(Long damageId);

    Long sumDamageCostsByMachineBetweenTwoDatesInMillis(Machine machine,
                                                        DamageStatus damageStatus,
                                                       Long startDate,
                                                       Long endDate);

}
