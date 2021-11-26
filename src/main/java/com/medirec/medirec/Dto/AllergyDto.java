package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model for allergy")
@Getter
@Setter
public class AllergyDto {
    
    @ApiModelProperty(notes = "Name of the allergen")
    private String allergen;
    
    @ApiModelProperty(notes = "Allergy type")
    private String type;
}
