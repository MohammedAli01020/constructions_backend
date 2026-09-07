package com.example.construction_api.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class LoanPage {

    @JsonProperty
    private int pageNumber = 0;
    @JsonProperty
    private int pageSize = 10;
    @JsonProperty
    private Sort.Direction sortDirection = Sort.Direction.DESC;
    @JsonProperty
    private String sortBy = "dateTime";

}
