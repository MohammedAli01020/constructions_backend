package com.example.construction_api.model.persisitece;

import com.example.construction_api.model.enums.CostTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "costs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    @Id
    @GeneratedValue
    @Column(name = "cost_id")
    private Long costId;

    @Column(name = "value")
    private Long value;

    @Column(name = "cost_types")
    @Enumerated(value = EnumType.STRING)
    private CostTypes costTypes;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "date_time")
    private Long dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

}
