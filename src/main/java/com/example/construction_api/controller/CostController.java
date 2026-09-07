package com.example.construction_api.controller;

import com.example.construction_api.model.persisitece.Cost;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.CostCriteria;
import com.example.construction_api.model.requests.CostPage;
import com.example.construction_api.model.requests.CreateCostRequest;
import com.example.construction_api.service.cost.CostService;
import com.example.construction_api.service.machine.MachineService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/costs/")
public class CostController {
    private final CostService costService;
    private final MachineService machineService;

    public CostController(CostService costService, MachineService machineService) {
        this.costService = costService;
        this.machineService = machineService;
    }


    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Cost> modify(@Valid @RequestBody CreateCostRequest createCostRequest) {

        Cost cost = new Cost();
        if (createCostRequest.getCostId() != null) {
            cost = costService.findById(createCostRequest.getCostId());
            if (cost == null) {
                return ResponseEntity.notFound().build();
            }
        }


        Machine machine = null;
        if (createCostRequest.getMachineId() != null) {

             machine = machineService.findById(createCostRequest.getMachineId());

            if (machine == null) {
                return ResponseEntity.notFound().build();
            }
        }


        cost.setValue(createCostRequest.getValue());
        cost.setCostTypes(createCostRequest.getCostTypes());
        cost.setNote(createCostRequest.getNote());
        cost.setDateTime(createCostRequest.getDateTime());
        cost.setMachine(machine);

        return new ResponseEntity<>(costService.save(cost), HttpStatus.OK);
    }


    @GetMapping("all")
    public ResponseEntity<Page<Cost>> getAllCostsWithFilters(CostPage costPage, CostCriteria costCriteria) {

        return new ResponseEntity<>(costService.
                getAllWithFilters(costPage, costCriteria), HttpStatus.OK);
    }

    @DeleteMapping("delete/id/{costId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long costId) {
        costService.deleteById(costId);
    }
}
