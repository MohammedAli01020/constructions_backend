package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.DamageStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDamageRequest {

    @JsonProperty
    private Long damageId;

    @JsonProperty
    @Size(max = 5000, message = "description should be max 5000 character")
    private String description;

    @JsonProperty
    @NotNull
    private Long insertDateTime;

    @JsonProperty
    private Long startDateTime;

    @JsonProperty
    private Long endDateTime;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    @NotNull
    @Enumerated(EnumType.STRING)
    private DamageStatus damageStatus;

    @JsonProperty
    private Long machineId;
}
