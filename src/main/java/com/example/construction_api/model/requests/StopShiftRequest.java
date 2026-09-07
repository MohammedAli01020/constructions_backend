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
public class StopShiftRequest {

    @JsonProperty
    @NotNull
    private Long shiftId;

    @JsonProperty

    private Long employeeId;

    @JsonProperty
    private Long machineId;


}
