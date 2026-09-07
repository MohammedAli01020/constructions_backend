package com.example.construction_api.controller;

import com.example.construction_api.model.enums.DamageStatus;
import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.requests.GlobalEmployeeStatisticsDto;
import com.example.construction_api.model.requests.GlobalMachineStatisticsDto;
import com.example.construction_api.model.requests.MachineDamagesResponse;
import com.example.construction_api.repository.EmployeeRepository;
import com.example.construction_api.repository.MachineRepository;
import com.example.construction_api.service.cost.CostService;
import com.example.construction_api.service.damage.DamageService;
import com.example.construction_api.service.loan.LoanService;
import com.example.construction_api.service.purchase.PurchaseService;
import com.example.construction_api.service.shift.ShiftService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/global_statistics/")
public class GlobalStatisticsController {

    private final MachineRepository machineRepository;
    private final EmployeeRepository employeeRepository;

    private final CostService costService;
    private final ShiftService shiftService;
    private final LoanService loanService;
    private final DamageService damageService;
    private final PurchaseService purchaseService;

    public GlobalStatisticsController(MachineRepository machineRepository, EmployeeRepository employeeRepository, CostService costService, ShiftService shiftService, LoanService loanService, DamageService damageService, PurchaseService purchaseService) {
        this.machineRepository = machineRepository;
        this.employeeRepository = employeeRepository;
        this.costService = costService;
        this.shiftService = shiftService;
        this.loanService = loanService;
        this.damageService = damageService;
        this.purchaseService = purchaseService;
    }

    @Transactional
    @GetMapping("fetch")
    public ResponseEntity<GlobalStatisticsResponse> fetch(Long startDate, Long endDate) {

        if (startDate == null) {
            startDate = 0L;
        }

        if (endDate == null) {
            endDate = System.currentTimeMillis();
        }

        // machine
        List<MachineDamagesResponse> damagesReport = machineRepository.fetchMachineDamagesLeftJoin(DamageStatus.REPAIRED,startDate, endDate);
        List<GlobalMachineStatisticsDto> costsReport = machineRepository.fetchMachineCostsLeftJoin(startDate, endDate);
        List<GlobalMachineStatisticsDto> shiftsReport = machineRepository.fetchMachineShiftsLeftJoin(ShiftStatus.FINISHED, startDate, endDate);

        // employee
        List<GlobalEmployeeStatisticsDto> loansReport = employeeRepository.fetchEmployeeLoansLeftJoin(startDate, endDate);
        List<GlobalEmployeeStatisticsDto> employeePricesReport = employeeRepository.fetchEmployeePricesLeftJoin(ShiftStatus.FINISHED, startDate, endDate);


        // sums
        Long damagesSum = damageService.sumDamageCostsBetweenTwoDatesInMillis(DamageStatus.REPAIRED, startDate, endDate);
        Long costsSum = costService.sumCostsValueBetweenTwoDatesInMillis(startDate, endDate);
        Long purchasesSum = purchaseService.sumPurchaseCostsBetweenTwoDatesInMillis(startDate, endDate);

        // where shift is finished
        Long shiftPricesSum = shiftService.getSumShiftPriceBetweenTwosDatesMillis(startDate, endDate);
        Long shiftEmployeePricesSum = shiftService.sumEmployeePricesBetweenTwoDatesInMillis(startDate, endDate);
        Long loansSum = loanService.getSumLoanValuesBetweenTwoDatesMillis(startDate, endDate);


        if (damagesSum == null) {
            damagesSum = 0L;
        }

        if (costsSum == null) {
            costsSum = 0L;
        }

        if (purchasesSum == null) {
            purchasesSum = 0L;
        }

        if (shiftPricesSum == null) {
            shiftPricesSum = 0L;
        }

        if (shiftEmployeePricesSum == null) {
            shiftEmployeePricesSum = 0L;
        }

        if (loansSum == null) {
            loansSum = 0L;
        }


        GlobalStatisticsResponse response = new GlobalStatisticsResponse();
        response.setCostsReport(costsReport);
        response.setShiftsReport(shiftsReport);
        response.setLoansReport(loansReport);
        response.setEmployeePricesReport(employeePricesReport);

        // set sums
        response.setDamagesReport(damagesReport);
        response.setDamagesSum(damagesSum);
        response.setCostsSum(costsSum);
        response.setShiftPricesSum(shiftPricesSum);
        response.setShiftEmployeePricesSum(shiftEmployeePricesSum);
        response.setLoansSum(loansSum);
        response.setPurchasesSum(purchasesSum);


        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
class GlobalStatisticsResponse {


    private List<MachineDamagesResponse> damagesReport;
    private List<GlobalMachineStatisticsDto> costsReport;
    private List<GlobalMachineStatisticsDto> shiftsReport;
    private List<GlobalEmployeeStatisticsDto> loansReport;
    private List<GlobalEmployeeStatisticsDto> employeePricesReport;
    private Long damagesSum;
    private Long costsSum;
    private Long shiftPricesSum;
    private Long shiftEmployeePricesSum;
    private Long loansSum;
    private Long purchasesSum;
}
