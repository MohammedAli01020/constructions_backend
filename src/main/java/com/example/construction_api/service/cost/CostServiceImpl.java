package com.example.construction_api.service.cost;

import com.example.construction_api.model.persisitece.Cost;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.CostCriteria;
import com.example.construction_api.model.requests.CostPage;
import com.example.construction_api.repository.CostCriteriaRepository;
import com.example.construction_api.repository.CostRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CostServiceImpl implements  CostService{

    private final CostRepository costRepository;
    private final CostCriteriaRepository costCriteriaRepository;

    public CostServiceImpl(CostRepository costRepository, CostCriteriaRepository costCriteriaRepository) {
        this.costRepository = costRepository;
        this.costCriteriaRepository = costCriteriaRepository;
    }

    @Override
    public Cost save(Cost cost) {
        return costRepository.save(cost);
    }

    @Override
    public Cost findById(Long costId) {
        return costRepository.findById(costId).orElseThrow();
    }

    @Override
    public void deleteById(Long costId) {
        costRepository.deleteById(costId);
    }

    @Override
    public Page<Cost> getAllWithFilters(CostPage costPage, CostCriteria costCriteria) {
        return costCriteriaRepository.findAllWithFilters(costPage, costCriteria);
    }

    @Override
    public Long sumCostsValueByMachineBetweenTwoDatesInMillis(Machine machine, Long startDate, Long endDate) {
        return costRepository.sumCostsValueByMachineBetweenTwoDatesInMillis(machine, startDate, endDate);
    }

    @Override
    public Long sumCostsValueBetweenTwoDatesInMillis(Long startDate, Long endDate) {
        return costRepository.sumCostsValueBetweenTwoDatesInMillis(startDate, endDate);
    }
}
