package com.example.construction_api.security;

import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.repository.EmployerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsImpl implements UserDetailsService {


    final EmployerRepository employerRepository;

    public UserDetailsImpl(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employer employer  = employerRepository.findByUsername(username);

        if (employer == null) {
            throw  new UsernameNotFoundException("user not found");
        }

        return new CustomUserDetails(employer);
    }
}
