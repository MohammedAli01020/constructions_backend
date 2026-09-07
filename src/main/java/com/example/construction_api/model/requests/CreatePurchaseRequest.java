package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.CostTypes;
import com.example.construction_api.model.persisitece.Employer;
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
public class CreatePurchaseRequest {

    @JsonProperty
    private Long purchaseId;

    @JsonProperty
    @NotNull
    private Long cost;

    @JsonProperty
    @Size(max = 5000, message = "description should be max 5000 character")
    private String description;

    @JsonProperty
    @NotNull
    private Long dateTime;

    @JsonProperty
    private Long employerId;
}
