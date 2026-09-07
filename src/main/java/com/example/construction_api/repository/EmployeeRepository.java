package com.example.construction_api.repository;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.requests.GlobalEmployeeStatisticsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByEmployeeStatusAndFullNameIsLike(EmployeeStatus employeeStatus,
                                                         String fullName, Pageable pageable);

    List<Employee> findAllByEmployeeStatus(EmployeeStatus employeeStatus);

    @Modifying
    @Query("UPDATE Employee SET employeeStatus = :employeeStatus WHERE employeeId = :employeeId")
    void changeEmployeeState(@Param("employeeStatus") EmployeeStatus employeeStatus, @Param("employeeId") Long employeeId);

    @Query("SELECT new com.example.construction_api.model.requests.GlobalEmployeeStatisticsDto(employee.fullName, employee.employeeId, count (loan.loanId), sum (loan.value)) FROM Employee employee LEFT JOIN Loan loan " +
            "ON employee.employeeId = loan.employee.employeeId " +
            " WHERE loan.dateTime BETWEEN :startDate AND :endDate  GROUP BY employee.employeeId")
    List<GlobalEmployeeStatisticsDto> fetchEmployeeLoansLeftJoin(@Param("startDate") Long startDate,
                                                                 @Param("endDate") Long endDate);


    @Query("SELECT new com.example.construction_api.model.requests.GlobalEmployeeStatisticsDto(employee.fullName, employee.employeeId, count (shift.shiftId), sum (shift.employeePrice)) FROM Employee employee LEFT JOIN Shift shift " +
            "ON employee.employeeId = shift.employee.employeeId " +
            "where shift.shiftStatus =:shiftStatus  and shift.startDateTime BETWEEN :startDate AND :endDate GROUP BY employee.employeeId")
    List<GlobalEmployeeStatisticsDto> fetchEmployeePricesLeftJoin(
            @Param("shiftStatus") ShiftStatus shiftStatus,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);

}
