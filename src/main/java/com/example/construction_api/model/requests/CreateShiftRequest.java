package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.ShiftStatus;
import com.example.construction_api.model.enums.ShiftTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateShiftRequest {

    @JsonProperty
    private Long shiftId;

    @JsonProperty
    private Long employeeId;

    @JsonProperty
    private Long machineId;

    @JsonProperty
    @NotNull
    private Long startDateTime;

    @JsonProperty
    private Long endDateTime;

    @JsonProperty
    @NotNull
    private String location;


    @JsonProperty
    @NotNull
    private Long price;

    @JsonProperty
    @NotNull
    private Long employeePrice;

    @JsonProperty
    @NotNull
    @Enumerated(EnumType.STRING)
    private ShiftStatus shiftStatus;

    @JsonProperty
    @NotNull
    @Enumerated(EnumType.STRING)
    private ShiftTime shiftTime;
}
