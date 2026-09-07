package com.example.construction_api.service.employer;

import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.repository.EmployerRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployerServiceImpl implements EmployerService{
    private final EmployerRepository employerRepository;

    public EmployerServiceImpl(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Override
    public Employer findEmployerByUsername(String username) {
        return employerRepository.findByUsername(username);
    }

    @Override
    public Employer findEmployerById(Long employerId) {
        return employerRepository.findById(employerId).orElseThrow();
    }
}
