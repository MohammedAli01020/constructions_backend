package com.example.construction_api.service.loan;

import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Loan;
import com.example.construction_api.model.requests.LoanCriteria;
import com.example.construction_api.model.requests.LoanPage;
import com.example.construction_api.repository.LoanCriteriaRepository;
import com.example.construction_api.repository.LoanRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;
    private final LoanCriteriaRepository loanCriteriaRepository;

    public LoanServiceImpl(LoanRepository loanRepository, LoanCriteriaRepository loanCriteriaRepository) {
        this.loanRepository = loanRepository;
        this.loanCriteriaRepository = loanCriteriaRepository;
    }

    @Override
    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan findById(Long loanId) {
        return loanRepository.findById(loanId).orElseThrow();
    }

    @Override
    public Page<Loan> getAllWithFilters(LoanPage loanPage, LoanCriteria loanCriteria) {
        return loanCriteriaRepository.findAllWithFilters(loanPage, loanCriteria);
    }

    @Override
    public Long getSumLoanValuesByEmployeeAndDatesMillis(Employee employee, Long startDate, Long endDate) {
        return loanRepository.sumLoansValueByEmployeeBetweenTwoDatesInMillis(employee, startDate, endDate);
    }

    @Override
    public Long getSumLoanValuesBetweenTwoDatesMillis(Long startDate, Long endDate) {
        return loanRepository.sumLoansValueBetweenTwoDatesInMillis(startDate, endDate);
    }

    @Override
    public void deleteById(Long loanId) {
        loanRepository.deleteById(loanId);
    }
}
