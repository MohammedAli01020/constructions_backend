package com.example.construction_api.service.employee;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.requests.EmployeeCriteria;
import com.example.construction_api.model.requests.EmployeePage;
import com.example.construction_api.repository.EmployeeCriteriaRepository;
import com.example.construction_api.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private  final EmployeeRepository employeeRepository;
    private final EmployeeCriteriaRepository employeeCriteriaRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCriteriaRepository employeeCriteriaRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeCriteriaRepository = employeeCriteriaRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow();
    }

    @Override
    public Page<Employee> getAllWithFilters(EmployeePage employeePage, EmployeeCriteria employeeCriteria) {
        return employeeCriteriaRepository.findAllWithFilters(employeePage, employeeCriteria);
    }

    @Override
    public void changeEmployeeState(EmployeeStatus employeeStatus, Long employeeId) {
        employeeRepository.changeEmployeeState(employeeStatus, employeeId);
    }

    @Override
    public List<Employee> getAllEmployeesByStatus(EmployeeStatus employeeStatus) {
        return employeeRepository.findAllByEmployeeStatus(employeeStatus);
    }



}
