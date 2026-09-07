package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoanRequest {

    @JsonProperty
    private Long loanId;

    @JsonProperty
    private Long employeeId;

    @JsonProperty
    private Long employerId;

    @JsonProperty
    @NotNull
    private Long dateTime;


    @JsonProperty
    @NotNull
    private Long value;

    @JsonProperty
    @Size(max = 5000, message = "note should be max 5000 character")
    private String note;
}
