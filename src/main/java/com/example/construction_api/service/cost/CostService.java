package com.example.construction_api.service.cost;

import com.example.construction_api.model.persisitece.Cost;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.CostCriteria;
import com.example.construction_api.model.requests.CostPage;
import org.springframework.data.domain.Page;

public interface CostService {
    Cost save(Cost cost);
    Cost findById(Long costId);
    void deleteById(Long costId);
    Page<Cost> getAllWithFilters(CostPage costPage, CostCriteria costCriteria);


    Long sumCostsValueByMachineBetweenTwoDatesInMillis(Machine machine, Long startDate, Long endDate);
    Long sumCostsValueBetweenTwoDatesInMillis(Long startDate, Long endDate);


}
