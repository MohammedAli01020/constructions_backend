package com.example.construction_api.repository;

import com.example.construction_api.model.persisitece.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Employer findByUsername(String username);
}
