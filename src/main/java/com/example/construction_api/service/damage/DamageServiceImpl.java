package com.example.construction_api.service.damage;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.persisitece.Damage;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.DamageCriteria;
import com.example.construction_api.model.requests.DamagePage;
import com.example.construction_api.repository.DamageCriteriaRepository;
import com.example.construction_api.repository.DamageRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class DamageServiceImpl implements DamageService{

    private final DamageRepository damageRepository;
    private final DamageCriteriaRepository damageCriteriaRepository;

    public DamageServiceImpl(DamageRepository damageRepository, DamageCriteriaRepository damageCriteriaRepository) {
        this.damageRepository = damageRepository;
        this.damageCriteriaRepository = damageCriteriaRepository;
    }

    @Override
    public Damage save(Damage damage) {
        return damageRepository.save(damage);
    }

    @Override
    public Damage findById(Long damageId) {
        return damageRepository.findById(damageId).orElseThrow();
    }

    @Override
    public Page<Damage> getAllWithFilters(DamagePage damagePage, DamageCriteria damageCriteria) {
        return damageCriteriaRepository.findAllWithFilters(damagePage, damageCriteria);
    }

    @Override
    public void deleteById(Long damageId) {
        damageRepository.deleteById(damageId);
    }

    @Override
    public Long countAllByMachineAndDamageStatus(Machine machine, DamageStatus damageStatus) {
        return damageRepository.countAllByMachineAndDamageStatus(machine, damageStatus);
    }

    @Override
    public Long sumDamageCostsBetweenTwoDatesInMillis(DamageStatus damageStatus, Long startDate, Long endDate) {
        return damageRepository.sumDamageCostsBetweenTwoDatesInMillis(damageStatus, startDate, endDate);
    }

    @Override
    public Long sumDamageCostsByDamageBetweenTwoDatesInMillis(Long damageId) {
        return damageRepository.sumDamageCostsByDamageBetweenTwoDatesInMillis(damageId);
    }

    @Override
    public Long sumDamageCostsByMachineBetweenTwoDatesInMillis(Machine machine,DamageStatus damageStatus, Long startDate, Long endDate) {
        return damageRepository.sumDamageCostsByMachineBetweenTwoDatesInMillis(machine, damageStatus, startDate, endDate);
    }


}
