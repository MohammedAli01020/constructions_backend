package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DamageCriteria {

    @JsonProperty
    private Long machineId;

    @JsonProperty
    private String damageStatus;

    @JsonProperty
    private Long lessThanOrEqualToInsertDateTime;

    @JsonProperty
    private Long greaterThanOrEqualToInsertDateTime;

    @JsonProperty
    private Long lessThanOrEqualToStartDateTime;

    @JsonProperty
    private Long greaterThanOrEqualToStartDateTime;

    @JsonProperty
    private Long lessThanOrEqualToEndDateTime;

    @JsonProperty
    private Long greaterThanOrEqualToEndDateTime;

}
