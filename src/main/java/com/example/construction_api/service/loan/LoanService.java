package com.example.construction_api.service.loan;

import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Loan;
import com.example.construction_api.model.requests.LoanCriteria;
import com.example.construction_api.model.requests.LoanPage;

import org.springframework.data.domain.Page;

public interface LoanService {
    Loan save(Loan loan);

    Loan findById(Long loanId);

    Page<Loan> getAllWithFilters(LoanPage loanPage, LoanCriteria loanCriteria);

    Long getSumLoanValuesByEmployeeAndDatesMillis(Employee employee, Long startDate, Long endDate);
    Long getSumLoanValuesBetweenTwoDatesMillis(Long startDate, Long endDate);

    void deleteById(Long loanId);

}
