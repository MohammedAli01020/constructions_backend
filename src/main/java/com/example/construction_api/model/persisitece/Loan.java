package com.example.construction_api.model.persisitece;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employer employer;

    @Column(name = "date_time")
    private Long dateTime;


    @Column(name = "value")
    private Long value;


    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}
