package com.example.construction_api.model.persisitece;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "purchases")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue
    @Column(name = "purchase_id")
    private Long purchaseId;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_time")
    private Long dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id")
    private Employer employer;

}
