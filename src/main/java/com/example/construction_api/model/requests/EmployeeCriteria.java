package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCriteria {
    @JsonProperty
    private String employeeStatus;

    @JsonProperty
    private String fullNameOrPhoneNumber;
}
