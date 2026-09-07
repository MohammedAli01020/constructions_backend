package com.example.construction_api.model.persisitece;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "employer")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employer {

    @Id
    @GeneratedValue
    @Column(name = "employer_id")
    private Long employerId;


    @Column(name = "username", unique = true)
    private String username;


    @Column(name = "password")
    private String password;


    @Column(name = "full_name")
    private String fullName;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "create_date_time")
    private Long createDateTime;


    @Column(name = "enabled", columnDefinition = "boolean default true")
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @OneToMany(mappedBy = "employer")
    private Collection<Employee> employees;

    @OneToMany(mappedBy = "employer")
    private Collection<Machine> machines;

    @OneToMany(mappedBy = "employer")
    private Collection<Loan> loans;

    @OneToMany(mappedBy = "employer")
    private Collection<Purchase> purchases;

    @JsonIgnore
    public Collection<Loan> getLoans() {
        return loans;
    }

    @JsonIgnore
    public Collection<Machine> getMachines() {
        return machines;
    }

    @JsonIgnore
    public Collection<Employee> getEmployees() {
        return employees;
    }

    @JsonIgnore
    public Collection<Purchase> getPurchases() {
        return purchases;
    }
}
