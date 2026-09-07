package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MachineCriteria {
    @JsonProperty
    private String machineStatus;

    @JsonProperty
    private String machineTypes;

    @JsonProperty
    private String code;

}
