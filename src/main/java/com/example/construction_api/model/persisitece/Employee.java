package com.example.construction_api.model.persisitece;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "employee")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "fullName")
    private String fullName;


    @Column(name = "hire_date_time")
    private Long hireDateTime;


    @Column(name = "phone_number")
    private String phoneNumber;


    @Column(name = "id_number")
    private Long idNumber;


    @Column(name = "employee_status")
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;


    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @OneToMany(
            mappedBy = "employee")
    private Collection<Loan> loans;

    @OneToMany(
            mappedBy = "employee")
    private Collection<Shift> shifts;

    @JsonIgnore
    public Collection<Loan> getLoans() {
        return loans;
    }

    @JsonIgnore
    public Collection<Shift> getShifts() {
        return shifts;
    }

}
