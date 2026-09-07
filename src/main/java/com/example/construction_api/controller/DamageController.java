package com.example.construction_api.controller;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.persisitece.*;
import com.example.construction_api.model.requests.*;
import com.example.construction_api.service.damage.DamageService;
import com.example.construction_api.service.machine.MachineService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/damages/")
public class DamageController {

    private final DamageService damageService;
    private final MachineService machineService;

    public DamageController(DamageService damageService, MachineService machineService) {
        this.damageService = damageService;
        this.machineService = machineService;
    }

    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Damage> modify(@Valid @RequestBody CreateDamageRequest createDamageRequest) {

        Damage damage = new Damage();
        if (createDamageRequest.getDamageId() != null) {
            damage = damageService.findById(createDamageRequest.getDamageId());
            if (damage == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Machine machine = null;
        if (createDamageRequest.getMachineId() != null) {
             machine = machineService.findById(createDamageRequest.getMachineId());
            if (machine == null) {
                return ResponseEntity.notFound().build();
            }
        }


        assert machine != null;

        if (createDamageRequest.getDamageStatus() == DamageStatus.UNDER_REPAIR) {

            if (machine.getMachineStatus() == MachineStatus.RUNNING) {
                return ResponseEntity.badRequest().build();
            } else {
                machineService.changeMachineState(MachineStatus.REPAIRING, createDamageRequest.getMachineId());
                machine.setMachineStatus(MachineStatus.REPAIRING);
                createDamageRequest.setStartDateTime(System.currentTimeMillis());
            }

        }

        damage.setDescription(createDamageRequest.getDescription());
        damage.setInsertDateTime(createDamageRequest.getInsertDateTime());
        damage.setStartDateTime(createDamageRequest.getStartDateTime());
        damage.setEndDateTime(createDamageRequest.getEndDateTime());
        damage.setMachine(machine);
        damage.setImageUrl(createDamageRequest.getImageUrl());
        damage.setDamageStatus(createDamageRequest.getDamageStatus());

        return new ResponseEntity<>(damageService.save(damage), HttpStatus.OK);

    }

    @Transactional
    @PostMapping("start")
    public ResponseEntity<Damage> start(@Valid @RequestBody ModifyDamageRequest modifyDamageRequest) {

        Damage damage = damageService.findById(modifyDamageRequest.getDamageId());

        if ( damage == null) {
            return ResponseEntity.notFound().build();
        }


        Machine machine = null;
        if(modifyDamageRequest.getMachineId() != null) {
             machine = machineService.findById(modifyDamageRequest.getMachineId());
            if (machine == null ) {
                return ResponseEntity.notFound().build();
            }
        }

        assert machine != null;

        if (machine.getMachineStatus() == MachineStatus.RUNNING) {
            return ResponseEntity.badRequest().build();
        } else {
            if (machine.getMachineStatus() != MachineStatus.REPAIRING) {
                machineService.changeMachineState(MachineStatus.REPAIRING, modifyDamageRequest.getMachineId());
                machine.setMachineStatus(MachineStatus.REPAIRING);
            }
        }

        damage.setMachine(machine);
        damage.setStartDateTime(System.currentTimeMillis());
        damage.setDamageStatus(DamageStatus.UNDER_REPAIR);


        return new ResponseEntity<>(damageService.save(damage), HttpStatus.OK);

    }

    @Transactional
    @PostMapping("finish")
    public ResponseEntity<Damage> finish(@Valid @RequestBody ModifyDamageRequest modifyDamageRequest) {


        Damage damage = new Damage();
        Machine machine = null;

        if (modifyDamageRequest.getDamageId() != null) {
             damage = damageService.findById(modifyDamageRequest.getDamageId());

            if (damage == null) {
                return ResponseEntity.notFound().build();
            }
        }


        if (modifyDamageRequest.getMachineId() != null) {
            machine = machineService.findById(modifyDamageRequest.getMachineId());

            if (machine == null) {
                return ResponseEntity.notFound().build();
            }
        }

        assert machine != null;

        if (damageService.countAllByMachineAndDamageStatus(machine, DamageStatus.UNDER_REPAIR) == 1) {
            machineService.changeMachineState(MachineStatus.READY, modifyDamageRequest.getMachineId());
            machine.setMachineStatus(MachineStatus.READY);
        }


        damage.setMachine(machine);
        damage.setEndDateTime(System.currentTimeMillis());
        damage.setDamageStatus(DamageStatus.REPAIRED);

        return new ResponseEntity<>(damageService.save(damage), HttpStatus.OK);

    }

    @GetMapping("all")
    public ResponseEntity<Page<Damage>> getAllDamagesWithFilters(DamagePage damagePage, DamageCriteria damageCriteria) {
        return new ResponseEntity<>(damageService.
                getAllWithFilters(damagePage, damageCriteria), HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("delete/id/{damageId}")
    public ResponseEntity<Void> delete(@PathVariable Long damageId, Long machineId) {

        if (machineId != null) {
            Machine machine = machineService.findById(machineId);

            if (machine == null) {
                return ResponseEntity.notFound().build();
            }

            if (machine.getMachineStatus() == MachineStatus.REPAIRING) {
                machineService.changeMachineState(MachineStatus.READY, machineId);
            }
        }

        damageService.deleteById(damageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("sum/id/{damageId}")
    public ResponseEntity<Long> sum(@PathVariable Long damageId) {

        Long sumDamage = damageService.sumDamageCostsByDamageBetweenTwoDatesInMillis(damageId);


        if (sumDamage == null) {
            sumDamage = 0L;
        }
        return new ResponseEntity<>(sumDamage, HttpStatus.OK);
    }

}
