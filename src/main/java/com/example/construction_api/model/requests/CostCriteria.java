package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostCriteria {

    @JsonProperty
    private Long machineId;

    @JsonProperty
    private Long lessThanOrEqualToDateTime;

    @JsonProperty
    private Long greaterThanOrEqualToDateTime;

    @JsonProperty
    private String costTypes;
}
