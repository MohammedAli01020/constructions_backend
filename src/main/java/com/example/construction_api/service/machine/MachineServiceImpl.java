package com.example.construction_api.service.machine;
import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.MachineCriteria;
import com.example.construction_api.model.requests.MachinePage;
import com.example.construction_api.repository.MachineCriteriaRepository;
import com.example.construction_api.repository.MachineRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineServiceImpl implements MachineService{
    private final MachineRepository machineRepository;
    private final MachineCriteriaRepository machineCriteriaRepository;

    public MachineServiceImpl(MachineRepository machineRepository, MachineCriteriaRepository machineCriteriaRepository) {
        this.machineRepository = machineRepository;
        this.machineCriteriaRepository = machineCriteriaRepository;
    }

    @Override
    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public Machine findById(Long machineId) {
        return machineRepository.findById(machineId).orElseThrow();
    }

    @Override
    public Machine findByCode(String code) {
        return machineRepository.findByCode(code);
    }

    @Override
    public Page<Machine> getAllWithFilters(MachinePage machinePage, MachineCriteria machineCriteria) {
        return machineCriteriaRepository.findAllWithFilters(machinePage, machineCriteria);
    }

    @Override
    public void changeMachineState(MachineStatus machineStatus, Long machineId) {
        machineRepository.changeMachineState(machineStatus, machineId);
    }

    @Override
    public List<Machine> getAllMachinesByStatus(MachineStatus machineStatus) {
        return machineRepository.findAllByMachineStatus(machineStatus);
    }

    @Override
    public void deleteById(Long machineId) {
        machineRepository.deleteById(machineId);
    }


}
