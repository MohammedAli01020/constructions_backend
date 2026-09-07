package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Transactional
    @Query("SELECT sum(loan.value) FROM Loan loan where loan.employee = :employee and loan.dateTime between :startDate and :endDate")
    Long sumLoansValueByEmployeeBetweenTwoDatesInMillis(@Param("employee") Employee employee,
                                                        @Param("startDate") Long startDate,
                                                        @Param("endDate") Long endDate);


    @Transactional
    @Query("SELECT sum(loan.value) FROM Loan loan where loan.dateTime between :startDate and :endDate")
    Long sumLoansValueBetweenTwoDatesInMillis(
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);

}
