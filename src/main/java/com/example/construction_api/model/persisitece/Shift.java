package com.example.construction_api.model.persisitece;

import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.enums.ShiftTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shifts")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Long shiftId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Machine machine;

    @Column(name = "start_date_time")
    private Long startDateTime;

    @Column(name = "end_date_time")
    private Long endDateTime;

    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private Long price;

    @Column(name = "employee_price")
    private Long employeePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_status")
    private ShiftStatus shiftStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "shift_time")
    private ShiftTime shiftTime;
}
