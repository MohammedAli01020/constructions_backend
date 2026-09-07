package com.example.construction_api.controller;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.example.construction_api.model.persisitece.Employee;
import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.model.requests.CreateEmployeeRequest;
import com.example.construction_api.model.requests.EmployeeCriteria;
import com.example.construction_api.model.requests.EmployeePage;
import com.example.construction_api.service.employee.EmployeeService;
import com.example.construction_api.service.employer.EmployerService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployerService employerService;


    public EmployeeController(EmployeeService employeeService, EmployerService employerService) {
        this.employeeService = employeeService;
        this.employerService = employerService;

    }


    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("welcome", HttpStatus.OK);
    }

    @Transactional
    @PostMapping("modify")
    public ResponseEntity<Employee> modify(@Valid @RequestBody CreateEmployeeRequest createEmployeeRequest) {

        Employee employee = new Employee();

        if (createEmployeeRequest.getEmployeeId() != null) {
            employee = employeeService.findEmployeeById(createEmployeeRequest.getEmployeeId());
            if (employee == null) {
                return ResponseEntity.notFound().build();
            }
        }

        Employer employer = null;
        if (createEmployeeRequest.getEmployerId() != null) {
             employer = employerService.findEmployerById(createEmployeeRequest.getEmployerId());
            if (employer == null) {
                return ResponseEntity.notFound().build();
            }
        }


        employee.setEmployer(employer);
        employee.setFullName(createEmployeeRequest.getFullName());
        employee.setHireDateTime(createEmployeeRequest.getHireDateTime());
        employee.setIdNumber(createEmployeeRequest.getIdNumber());
        employee.setEmployeeStatus(createEmployeeRequest.getEmployeeStatus());
        employee.setPhoneNumber(createEmployeeRequest.getPhoneNumber());


        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.OK);

    }

    @GetMapping("all")
    public ResponseEntity<Page<Employee>> getAllEmployeesWithFilters(EmployeePage employeePage, EmployeeCriteria employeeCriteria) {

        return new ResponseEntity<>(employeeService.getAllWithFilters(employeePage, employeeCriteria), HttpStatus.OK);
    }


    @GetMapping("allByEmployeeStatus")
    public ResponseEntity<List<Employee>> finalAll(String employeeStatus) {
        return new ResponseEntity<>(employeeService.getAllEmployeesByStatus(EmployeeStatus.valueOf(employeeStatus)), HttpStatus.OK);
    }

}
