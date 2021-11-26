package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model for family background")
@Setter
@Getter
public class FamilyBackgroundDto {
    
    @ApiModelProperty(notes = "Relative's kinship")
    private String familyMember;
    
    @ApiModelProperty(notes = "Background description")
    private String description;
}
