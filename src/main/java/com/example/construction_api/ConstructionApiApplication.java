package com.example.construction_api;

import com.example.construction_api.model.persisitece.Employer;
import com.example.construction_api.model.persisitece.Role;
import com.example.construction_api.repository.EmployerRepository;
import com.example.construction_api.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories("com.example.construction_api.repository")
@EntityScan("com.example.construction_api.model.persisitece")
public class ConstructionApiApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public static void main(String[] args) {
        SpringApplication.run(ConstructionApiApplication.class, args);
    }

    @Bean
    CommandLineRunner createUser(EmployerRepository employerRepository, RoleRepository roleRepository) {

        return args -> {

//            Employer employer = new Employer("ssmm", bCryptPasswordEncoder().encode("000011"), Collections.emptyList());

            Employer employer = employerRepository.findByUsername("ahmed@gmail.com");

            if (employer == null) {
                employer = new Employer();
                Role role = roleRepository.save(new Role("ROLE_admin"));

                employer.setRoles(Collections.singleton(role));
                employer.setUsername("ahmed@gmail.com");
                employer.setPassword(bCryptPasswordEncoder().encode("1234567"));
                employer.setFullName("ahmed");
                employer.setPhoneNumber("01022032143");
                employer.setEnabled(true);
                employer.setCreateDateTime(System.currentTimeMillis());

                employerRepository.save(employer);

            }


        };



    }
}
