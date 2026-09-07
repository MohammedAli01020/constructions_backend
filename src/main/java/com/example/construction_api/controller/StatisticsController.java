package com.example.construction_api.controller;


import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.service.cost.CostService;
import com.example.construction_api.service.damage.DamageService;
import com.example.construction_api.service.employee.EmployeeService;
import com.example.construction_api.service.loan.LoanService;
import com.example.construction_api.service.machine.MachineService;
import com.example.construction_api.service.shift.ShiftService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/api/statistics/")
public class StatisticsController {

    private final ShiftService shiftService;
    private final LoanService loanService;
    private final EmployeeService employeeService;
    private final MachineService machineService;
    private final CostService costService;
    private final DamageService damageService;


    public StatisticsController(ShiftService shiftService, LoanService loanService, EmployeeService employeeService, MachineService machineService, CostService costService, DamageService damageService) {
        this.shiftService = shiftService;
        this.loanService = loanService;
        this.employeeService = employeeService;
        this.machineService = machineService;
        this.costService = costService;
        this.damageService = damageService;
    }


    @Transactional
    @GetMapping("employee/{employeeId}")
    public ResponseEntity<EmployeeStatisticsResponse> getEmployeeStatistics(@PathVariable Long employeeId, Long startDate, Long endDate) {

        Employee employee = employeeService.findEmployeeById(employeeId);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }


        if (startDate == null) {
            startDate = 0L;
        }

        if (endDate == null) {
            endDate = System.currentTimeMillis();
        }


        Long totalShiftsPrice = shiftService.getSumShiftEmployeePricesByEmployeeAndDatesMillis(employee, startDate, endDate);
        Long totalLoansValues = loanService.getSumLoanValuesByEmployeeAndDatesMillis(employee, startDate, endDate);

        if (totalShiftsPrice == null) {
            totalShiftsPrice = 0L;
        }

        if (totalLoansValues == null) {
            totalLoansValues = 0L;
        }

        return new ResponseEntity<>
                (new EmployeeStatisticsResponse(totalShiftsPrice, totalLoansValues), HttpStatus.OK);
    }


    @Transactional
    @GetMapping("machine/{machineId}")
    public ResponseEntity<MachineStatisticsResponse> getMachineStatistics(@PathVariable Long machineId, Long startDate, Long endDate) {

        Machine machine = machineService.findById(machineId);

        if (machine == null) {
            return ResponseEntity.notFound().build();
        }


        if (startDate == null) {
            startDate = 0L;
        }

        if (endDate == null) {
            endDate = System.currentTimeMillis();
        }

        Long totalCostsPrice = costService.sumCostsValueByMachineBetweenTwoDatesInMillis(machine, startDate, endDate);
        Long totalMaintenancesValues = damageService.sumDamageCostsByMachineBetweenTwoDatesInMillis(machine, DamageStatus.REPAIRED, startDate, endDate);

        if (totalCostsPrice == null) {
            totalCostsPrice = 0L;
        }

        if (totalMaintenancesValues == null) {
            totalMaintenancesValues = 0L;
        }

        return new ResponseEntity<>
                (new MachineStatisticsResponse(totalCostsPrice, totalMaintenancesValues), HttpStatus.OK);
    }


}

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class EmployeeStatisticsResponse {
    private Long totalShiftsPrice;
    private Long totalLoansValues;
}


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class MachineStatisticsResponse {
    private Long totalCostsPrice;
    private Long totalMaintenancesValues;
}
