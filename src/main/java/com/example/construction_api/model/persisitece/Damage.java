package com.example.construction_api.model.persisitece;

import com.example.construction_api.model.enums.DamageStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "damages")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Damage {

    @Id
    @GeneratedValue
    @Column(name = "damage_id")
    private Long damageId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "insert_date_time")
    private Long insertDateTime;

    @Column(name = "start_date_time")
    private Long startDateTime;

    @Column(name = "end_date_time")
    private Long endDateTime;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;


    @Column(name = "damage_status")
    @Enumerated(EnumType.STRING)
    private DamageStatus damageStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @OneToMany(mappedBy = "damage")
    private Collection<DamageItem> damageItems;


    @JsonIgnore
    public Collection<DamageItem> getDamageItems() {
        return damageItems;
    }
}
