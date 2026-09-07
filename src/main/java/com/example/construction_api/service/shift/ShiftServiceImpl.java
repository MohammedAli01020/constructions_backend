package com.example.construction_api.service.shift;

import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Shift;
import com.example.construction_api.model.requests.ShiftCriteria;
import com.example.construction_api.model.requests.ShiftPage;
import com.example.construction_api.repository.ShiftCriteriaRepository;
import com.example.construction_api.repository.ShiftRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class ShiftServiceImpl implements ShiftService{
    private final ShiftRepository shiftRepository;
    private final ShiftCriteriaRepository shiftCriteriaRepository;
    public ShiftServiceImpl(ShiftRepository shiftRepository, ShiftCriteriaRepository shiftCriteriaRepository) {
        this.shiftRepository = shiftRepository;
        this.shiftCriteriaRepository = shiftCriteriaRepository;
    }

    @Override
    public Shift save(Shift shift) {
        return shiftRepository.save(shift);
    }

    @Override
    public Shift findById(Long shiftId) {
        return shiftRepository.findById(shiftId).orElseThrow();
    }

    @Override
    public Page<Shift> getAllWithFilters(ShiftPage shiftPage, ShiftCriteria shiftCriteria) {
        return shiftCriteriaRepository.findAllWithFilters(shiftPage, shiftCriteria);
    }

    @Override
    public Long getSumShiftEmployeePricesByEmployeeAndDatesMillis(Employee employee, Long startDate, Long endDate) {
        return shiftRepository.sumPriceByEmployeeBetweenTwoDatesInMillis(employee, ShiftStatus.FINISHED,startDate, endDate);
    }

    @Override
    public Long getSumShiftPriceBetweenTwosDatesMillis(Long startDate, Long endDate) {
        return shiftRepository.sumPriceBetweenTwoDatesInMillis(ShiftStatus.FINISHED,startDate, endDate);
    }

    @Override
    public Long sumEmployeePricesBetweenTwoDatesInMillis(Long startDate, Long endDate) {
        return shiftRepository.sumEmployeePricesBetweenTwoDatesInMillis(ShiftStatus.FINISHED,startDate, endDate);
    }

    @Override
    public void deleteById(Long shiftId) {
        shiftRepository.deleteById(shiftId);
    }


}
