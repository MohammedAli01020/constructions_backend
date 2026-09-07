package com.example.construction_api.controller;

import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.service.employer.EmployerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employers/")
public class EmployerController {

    private final EmployerService employerService;


    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }


    @GetMapping("id/{employerId}")
    public ResponseEntity<Employer> findEmployerById(@PathVariable Long employerId) {
        return new ResponseEntity<>(employerService.findEmployerById(employerId), HttpStatus.OK);
    }

}
