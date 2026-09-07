package com.example.construction_api.model.persisitece;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "damage_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DamageItem {

    @Id
    @GeneratedValue
    @Column(name = "damage_item_id")
    private Long damageItemId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "insert_date_time")
    private Long insertDateTime;

    @Column(name = "cost")
    private Long cost;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "damageId")
    private Damage damage;
}
