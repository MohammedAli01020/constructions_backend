package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.MachineStatus;
import com.example.construction_api.model.enums.MachineTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMachineRequest {


    @JsonProperty
    private Long machineId;

    @JsonProperty
    @NotBlank
    private String code;

    @JsonProperty
    @NotNull
    @Enumerated(EnumType.STRING)
    private MachineTypes machineTypes;

    @JsonProperty
    @NotNull
    private Long insertDataTime;


    @JsonProperty
    @NotNull
    @Enumerated(EnumType.STRING)
    private MachineStatus machineStatus;

    @JsonProperty
    private Long employerId;


}
