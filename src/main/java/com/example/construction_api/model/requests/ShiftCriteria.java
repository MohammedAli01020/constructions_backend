package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShiftCriteria {

    @JsonProperty
    private Long lessThanOrEqualToStartDateTime;

    @JsonProperty
    private Long greaterThanOrEqualToStartDateTime;

    @JsonProperty
    private String shiftStatus;

    @JsonProperty
    private Long employeeId;

    @JsonProperty
    private Long machineId;

}
