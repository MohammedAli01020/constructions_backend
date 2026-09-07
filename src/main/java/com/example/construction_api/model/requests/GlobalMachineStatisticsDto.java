package com.example.construction_api.model.requests;

import com.example.construction_api.model.enums.MachineTypes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GlobalMachineStatisticsDto {
    private String name;
    private String id;
    private Long count;
    private Long sum;


    public GlobalMachineStatisticsDto(@NotNull MachineTypes name, String id, Long count, Long sum) {
        this.name = name.name();
        this.id = id;
        this.count = count;
        this.sum = sum;
    }

}
