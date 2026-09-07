package com.example.construction_api.model.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalEmployeeStatisticsDto {

    private String name;
    private Long id;
    private Long count;
    private Long sum;

}
