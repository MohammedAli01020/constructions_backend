package com.example.construction_api.service.employee;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Machine;
import com.example.construction_api.model.requests.EmployeeCriteria;
import com.example.construction_api.model.requests.EmployeePage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);

    Employee findEmployeeById(Long employeeId);

    Page<Employee> getAllWithFilters(EmployeePage employeePage, EmployeeCriteria employeeCriteria);

    void changeEmployeeState(EmployeeStatus employeeStatus, Long employeeId);

    List<Employee> getAllEmployeesByStatus(EmployeeStatus employeeStatus);
}
