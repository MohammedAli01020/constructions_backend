package com.example.construction_api.model.persisitece;

import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.enums.MachineTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "machines")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Machine {
    @Id
    @GeneratedValue
    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "machine_types")
    @Enumerated(EnumType.STRING)
    private MachineTypes machineTypes;

    @Column(name = "insert_data_time")
    private Long insertDataTime;

    @OneToMany(mappedBy = "machine")
    private Collection<Damage> damages;

    @OneToMany(
            mappedBy = "machine")
    private Collection<Cost> costs;

    @Column(name = "machine_status")
    @Enumerated(EnumType.STRING)
    private MachineStatus machineStatus;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;


    @OneToMany(
            mappedBy = "machine")
    private Collection<Shift> shifts;


    @JsonIgnore
    public Collection<Shift> getShifts() {
        return shifts;
    }

    @JsonIgnore
    public Collection<Damage> getDamages() {
        return damages;
    }

    @JsonIgnore
    public Collection<Cost> getCosts() {
        return costs;
    }

}



