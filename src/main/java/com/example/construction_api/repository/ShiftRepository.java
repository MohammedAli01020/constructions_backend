package com.example.construction_api.repository;

import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    @Transactional
    @Query("SELECT sum(shift.employeePrice ) FROM Shift shift where  shift.employee = :employee and shift.shiftStatus = :shiftStatus and shift.startDateTime between :startDate and :endDate")
    Long sumPriceByEmployeeBetweenTwoDatesInMillis(@Param("employee") Employee employee,
                                                   @Param("shiftStatus") ShiftStatus shiftStatus,
                                                   @Param("startDate") Long startDate,
                                                   @Param("endDate") Long endDate);


    @Transactional
    @Query("SELECT sum(shift.employeePrice ) FROM Shift shift where  shift.shiftStatus = :shiftStatus and shift.startDateTime between :startDate and :endDate")
    Long sumEmployeePricesBetweenTwoDatesInMillis(
            @Param("shiftStatus") ShiftStatus shiftStatus,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);


    @Transactional
    @Query("SELECT sum(shift.price ) FROM Shift shift where shift.shiftStatus = :shiftStatus and shift.startDateTime between :startDate and :endDate")
    Long sumPriceBetweenTwoDatesInMillis(
            @Param("shiftStatus") ShiftStatus shiftStatus,
            @Param("startDate") Long startDate,
            @Param("endDate") Long endDate);
}
