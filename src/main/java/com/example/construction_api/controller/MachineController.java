package com.example.construction_api.controller;

import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.CreateMachineRequest;
import com.example.construction_api.model.requests.MachineCriteria;
import com.example.construction_api.model.requests.MachinePage;
import com.example.construction_api.service.employer.EmployerService;
import com.example.construction_api.service.machine.MachineService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/machines/")
public class MachineController {
    private final MachineService machineService;
    private final EmployerService employerService;

    public MachineController(MachineService machineService, EmployerService employerService) {
        this.machineService = machineService;
        this.employerService = employerService;
    }


    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Machine> modify(@Valid @RequestBody CreateMachineRequest createMachineRequest) {

        Machine machine = new Machine();

        if (createMachineRequest.getMachineId() != null) {
            machine = machineService.findById(createMachineRequest.getMachineId());
            if (machine == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Employer employer = null;
        if (createMachineRequest.getEmployerId() != null) {
             employer = employerService.findEmployerById(createMachineRequest.getEmployerId());
            if (employer == null) {
                return ResponseEntity.notFound().build();
            }
        }


        machine.setEmployer(employer);
        machine.setCode(createMachineRequest.getCode());
        machine.setMachineTypes(createMachineRequest.getMachineTypes());
        machine.setInsertDataTime(createMachineRequest.getInsertDataTime());
        machine.setMachineStatus(createMachineRequest.getMachineStatus());


        return new ResponseEntity<>(machineService.save(machine), HttpStatus.OK);

    }

    @GetMapping("all")
    public ResponseEntity<Page<Machine>> getAllMachinesWithFilters(MachinePage machinePage, MachineCriteria machineCriteria) {

        return new ResponseEntity<>(machineService.
                getAllWithFilters(machinePage, machineCriteria), HttpStatus.OK);
    }


    @GetMapping("allByMachineStatus")
    public ResponseEntity<List<Machine>> finalAll(String machineStatus) {
        return new ResponseEntity<>(machineService.getAllMachinesByStatus(MachineStatus.valueOf(machineStatus)), HttpStatus.OK);
    }



    @DeleteMapping("delete/id/{machineId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long machineId) {
        machineService.deleteById(machineId);
    }


}
