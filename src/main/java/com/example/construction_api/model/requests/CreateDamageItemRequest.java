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
public class CreateDamageItemRequest {

    @JsonProperty
    private Long damageItemId;

    @JsonProperty
    @Size(max = 5000, message = "description should be max 5000 character")
    private String description;

    @JsonProperty
    @NotNull
    private Long insertDateTime;

    @JsonProperty
    @NotNull
    private Long cost;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    @NotNull
    private Long damageId;
}
