package com.medirec.medirec.Dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("Model for illness")
@Getter
@Setter
public class IllnessDto {

    @ApiModelProperty(notes = "Name of the illness")
    private String illnessName;
    
    @ApiModelProperty(notes = "Detection date in format DD/MM/YYYY", example = "01/10/2010")
    private String detectionDate;
    
    @ApiModelProperty(notes = "Description of the illness")
    private String illnessDescription;

}
