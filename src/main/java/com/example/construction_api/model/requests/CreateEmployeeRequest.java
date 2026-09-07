package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.EmployeeStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    @JsonProperty
    private Long employeeId;

    @JsonProperty
    @NotBlank
    @Size(min = 2, max = 50, message = "body should be min 2 and max 50 character")
    private String fullName;


    @JsonProperty
    @NotNull
    private Long hireDateTime;


    @JsonProperty
    private String phoneNumber;


    @JsonProperty
    private Long idNumber;


    @JsonProperty
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;


    @JsonProperty
    private Long employerId;
}
