package com.example.construction_api.controller;

import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.model.persisitece.Loan;
import com.example.construction_api.model.persisitece.Purchase;
import com.example.construction_api.model.requests.CreateLoanRequest;
import com.example.construction_api.model.requests.LoanCriteria;
import com.example.construction_api.model.requests.LoanPage;
import com.example.construction_api.service.employee.EmployeeService;
import com.example.construction_api.service.employer.EmployerService;
import com.example.construction_api.service.loan.LoanService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/loans/")
public class LoanController {

    private final LoanService loanService;
    private final EmployeeService employeeService;
    protected final EmployerService employerService;

    public LoanController(LoanService loanService, EmployeeService employeeService, EmployerService employerService) {
        this.loanService = loanService;
        this.employeeService = employeeService;
        this.employerService = employerService;
    }

    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Loan> modify(@Valid @RequestBody CreateLoanRequest createLoanRequest) {

        Loan loan = new Loan();
        if (createLoanRequest.getLoanId() != null) {
            loan = loanService.findById(createLoanRequest.getLoanId());
            if (loan == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Employee employee = null;
        if (createLoanRequest.getEmployeeId() != null) {
             employee = employeeService.findEmployeeById(createLoanRequest.getEmployeeId());
            if (employee == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Employer employer = null;
        if (createLoanRequest.getEmployerId() != null) {
             employer = employerService.findEmployerById(createLoanRequest.getEmployerId());

            if (employer == null) {
                return ResponseEntity.notFound().build();
            }
        }



        loan.setEmployee(employee);
        loan.setEmployer(employer);
        loan.setDateTime(createLoanRequest.getDateTime());

        loan.setValue(createLoanRequest.getValue());
        loan.setNote(createLoanRequest.getNote());

        return new ResponseEntity<>(loanService.save(loan), HttpStatus.OK);

    }


    @GetMapping("all")
    public ResponseEntity<Page<Loan>> getAllLoansWithFilters(LoanPage loanPage, LoanCriteria loanCriteria) {

        return new ResponseEntity<>(loanService.
                getAllWithFilters(loanPage, loanCriteria), HttpStatus.OK);
    }



    @DeleteMapping("delete/id/{loanId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long loanId) {
        loanService.deleteById(loanId);
    }

}
