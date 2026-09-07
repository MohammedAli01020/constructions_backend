package com.example.construction_api.service.employer;

import com.example.construction_api.model.persisitece.Employer;

public interface EmployerService {
    Employer findEmployerByUsername(String username);
    Employer findEmployerById(Long employerId);

}
