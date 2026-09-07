package com.example.construction_api.service.machine;

import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.MachineCriteria;
import com.example.construction_api.model.requests.MachinePage;
import org.springframework.data.domain.Page;

import java.util.List;


public interface MachineService {
    Machine save(Machine machine);
    Machine findById(Long machineId);
    Machine findByCode(String code);

    Page<Machine> getAllWithFilters(MachinePage machinePage, MachineCriteria machineCriteria);
    void changeMachineState(MachineStatus machineStatus, Long machineId);

    List<Machine> getAllMachinesByStatus(MachineStatus machineStatus);



    void deleteById(Long machineId);
}
