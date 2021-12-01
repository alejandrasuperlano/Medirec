package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model to update patient's info")
@Getter
@Setter
public class PatientUpdateInfoDto {
    @ApiModelProperty(notes = "Patient's gender")
    private String gender;
    
    @ApiModelProperty(notes = "Patient's home address")
    private String homeAddress;
    
    @ApiModelProperty(notes = "Patient's EPS")
    private String eps;
    
    
    @ApiModelProperty(notes = "Patient's marital status")
    private String maritalStatus;
}

