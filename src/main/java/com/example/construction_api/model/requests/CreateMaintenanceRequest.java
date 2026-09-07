package com.example.construction_api.model.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMaintenanceRequest {

    @JsonProperty
    private Long maintenanceId;

    @JsonProperty
    @NotNull
    private Long value;

    @JsonProperty
    private String description;


    @JsonProperty
    @NotNull
    private Long dateTime;


    @JsonProperty
    private Long machineId;
}
