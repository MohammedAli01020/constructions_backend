package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.CostTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCostRequest {

    @JsonProperty
    private Long costId;

    @JsonProperty
    @NotNull
    private Long value;

    @JsonProperty
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private CostTypes costTypes;

    @JsonProperty
    @Size(max = 5000, message = "note should be max 5000 character")
    private String note;

    @JsonProperty
    @NotNull
    private Long dateTime;

    @JsonProperty
    private Long machineId;
}
