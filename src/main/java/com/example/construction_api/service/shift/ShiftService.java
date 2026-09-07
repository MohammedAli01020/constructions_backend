package com.example.construction_api.service.shift;

import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Shift;
import com.example.construction_api.model.requests.ShiftCriteria;
import com.example.construction_api.model.requests.ShiftPage;
import org.springframework.data.domain.Page;

public interface ShiftService {
    Shift save(Shift shift);

    Shift findById(Long shiftId);

    Page<Shift> getAllWithFilters(ShiftPage shiftPage, ShiftCriteria shiftCriteria);

    Long getSumShiftEmployeePricesByEmployeeAndDatesMillis(Employee employee, Long startDate, Long endDate);

    Long getSumShiftPriceBetweenTwosDatesMillis(Long startDate, Long endDate);

    Long sumEmployeePricesBetweenTwoDatesInMillis(Long startDate, Long endDate);



    void deleteById(Long shiftId);
}
