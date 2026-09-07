package com.example.construction_api.controller;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.persisitece.Shift;
import com.example.construction_api.model.requests.CreateShiftRequest;
import com.example.construction_api.model.requests.ShiftCriteria;
import com.example.construction_api.model.requests.ShiftPage;
import com.example.construction_api.model.requests.StopShiftRequest;
import com.example.construction_api.service.employee.EmployeeService;
import com.example.construction_api.service.machine.MachineService;
import com.example.construction_api.service.shift.ShiftService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/shifts/")
public class ShiftController {

    private final ShiftService shiftService;
    private final EmployeeService employeeService;
    private final MachineService machineService;


    public ShiftController(ShiftService shiftService, EmployeeService employeeService, MachineService machineService) {
        this.shiftService = shiftService;
        this.employeeService = employeeService;
        this.machineService = machineService;
    }

    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Shift> modify(@Valid @RequestBody CreateShiftRequest createShiftRequest) {

        Shift shift = new Shift();

        if (createShiftRequest.getShiftId() != null) {
            shift = shiftService.findById(createShiftRequest.getShiftId());
            if (shift == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Employee employee = null;
        if (createShiftRequest.getEmployeeId() != null) {
             employee = employeeService.findEmployeeById(createShiftRequest.getEmployeeId());
            if (employee == null) {
                return ResponseEntity.notFound().build();
            }

            if (createShiftRequest.getShiftId() == null) {
                employeeService.changeEmployeeState(EmployeeStatus.RUNNING, createShiftRequest.getEmployeeId());
                employee.setEmployeeStatus(EmployeeStatus.RUNNING);
            }


            // update
            if (createShiftRequest.getShiftId() != null) {
                if (!Objects.equals(shift.getEmployee().getEmployeeId(), createShiftRequest.getEmployeeId()) && employee.getEmployeeStatus() == EmployeeStatus.RUNNING) {
                    return ResponseEntity.badRequest().build();
                }


                if (!Objects.equals(shift.getEmployee().getEmployeeId(), createShiftRequest.getEmployeeId())) {
                    employeeService.changeEmployeeState(EmployeeStatus.READY, shift.getEmployee().getEmployeeId());
                    employee.setEmployeeStatus(EmployeeStatus.READY);
                }



            } else if (employee.getEmployeeStatus() == EmployeeStatus.RUNNING ) {
                return ResponseEntity.badRequest().build();
            }
        }

        Machine machine = null;
        if (createShiftRequest.getMachineId() != null) {
             machine = machineService.findById(createShiftRequest.getMachineId());

            if (machine == null) {
                return ResponseEntity.notFound().build();
            }

            if (createShiftRequest.getShiftId() == null) {
                machineService.changeMachineState(MachineStatus.RUNNING, createShiftRequest.getMachineId());
                machine.setMachineStatus(MachineStatus.RUNNING);
            }


            // update
            if (createShiftRequest.getShiftId() != null) {


                if (!Objects.equals(shift.getMachine().getMachineId(), createShiftRequest.getMachineId()) && machine.getMachineStatus() == MachineStatus.RUNNING) {
                    return ResponseEntity.badRequest().build();
                }

                if (!Objects.equals(shift.getMachine().getMachineId(), createShiftRequest.getMachineId())) {
                    machineService.changeMachineState(MachineStatus.READY, shift.getMachine().getMachineId());
                    machine.setMachineStatus(MachineStatus.READY);
                }

            } else if (machine.getMachineStatus() == MachineStatus.RUNNING) {
                return ResponseEntity.badRequest().build();
            }

        }



        shift.setShiftStatus(createShiftRequest.getShiftStatus());
        shift.setEmployee(employee);
        shift.setMachine(machine);
        shift.setStartDateTime(createShiftRequest.getStartDateTime());
        shift.setEndDateTime(createShiftRequest.getEndDateTime());
        shift.setLocation(createShiftRequest.getLocation());
        shift.setPrice(createShiftRequest.getPrice());
        shift.setEmployeePrice(createShiftRequest.getEmployeePrice());
        shift.setShiftTime(createShiftRequest.getShiftTime());


        return new ResponseEntity<>(shiftService.save(shift), HttpStatus.OK);
    }

    @Transactional
    @PostMapping("finish")
    public ResponseEntity<Shift> finish(@Valid @RequestBody StopShiftRequest stopShiftRequest) {


        Shift shift = shiftService.findById(stopShiftRequest.getShiftId());

        if (shift == null) {
            return ResponseEntity.notFound().build();
        }


        Employee employee = null;
        if (stopShiftRequest.getEmployeeId() != null) {
             employee = employeeService.findEmployeeById(stopShiftRequest.getEmployeeId());
            if (employee == null) {
                return ResponseEntity.notFound().build();
            }

            employee.setEmployeeStatus(EmployeeStatus.READY);
            employeeService.changeEmployeeState(EmployeeStatus.READY, stopShiftRequest.getEmployeeId());
        }

        Machine machine = null;
        if (stopShiftRequest.getMachineId() != null) {
             machine = machineService.findById(stopShiftRequest.getMachineId());
            if (machine == null) {
                return ResponseEntity.notFound().build();
            }

            machine.setMachineStatus(MachineStatus.READY);

            machineService.changeMachineState(MachineStatus.READY, stopShiftRequest.getMachineId());
        }



        shift.setEmployee(employee);
        shift.setMachine(machine);

        shift.setEndDateTime(System.currentTimeMillis());
        shift.setShiftStatus(ShiftStatus.FINISHED);

        return new ResponseEntity<>(shiftService.save(shift), HttpStatus.OK);

    }


    @Transactional
    @PostMapping("cancel")
    public ResponseEntity<Shift> cancel(@Valid @RequestBody StopShiftRequest stopShiftRequest) {


        Shift shift = shiftService.findById(stopShiftRequest.getShiftId());

        if (shift == null) {
            return ResponseEntity.notFound().build();
        }

        Employee employee = null;
        if (stopShiftRequest.getEmployeeId() != null) {
             employee = employeeService.findEmployeeById(stopShiftRequest.getEmployeeId());

            if (employee == null) {
                return ResponseEntity.notFound().build();
            }

            employeeService.changeEmployeeState(EmployeeStatus.READY, stopShiftRequest.getEmployeeId());
            employee.setEmployeeStatus(EmployeeStatus.READY);
        }

        Machine machine = null;
        if (stopShiftRequest.getMachineId() != null) {
            machine = machineService.findById(stopShiftRequest.getMachineId());

            if (machine == null) {
                return ResponseEntity.notFound().build();
            }

            machineService.changeMachineState(MachineStatus.READY, stopShiftRequest.getMachineId());
            machine.setMachineStatus(MachineStatus.READY);
        }





        shift.setEmployee(employee);
        shift.setMachine(machine);


        shift.setEndDateTime(System.currentTimeMillis());
        shift.setShiftStatus(ShiftStatus.CANCELED);

        return new ResponseEntity<>(shiftService.save(shift), HttpStatus.OK);

    }


    @GetMapping("all")
    public ResponseEntity<Page<Shift>> getAllMachinesWithFilters(ShiftPage shiftPage, ShiftCriteria shiftCriteria) {

        return new ResponseEntity<>(shiftService.
                getAllWithFilters(shiftPage, shiftCriteria), HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("delete/id/{shiftId}")
//    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable Long shiftId, Long employeeId, Long machineId) {


        if (employeeId != null) {
            Employee employee = employeeService.findEmployeeById(employeeId);

            if (employee != null && employee.getEmployeeStatus() == EmployeeStatus.RUNNING) {
                employeeService.changeEmployeeState(EmployeeStatus.READY, employeeId);
            }
        }

        if (machineId != null) {
            Machine machine = machineService.findById(machineId);

            if (machine != null && machine.getMachineStatus() == MachineStatus.RUNNING) {
                machineService.changeMachineState(MachineStatus.READY, machineId);
            }
        }


        shiftService.deleteById(shiftId);

        return ResponseEntity.ok().build();
    }
}
