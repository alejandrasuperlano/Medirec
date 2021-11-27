package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model to update doctor's info")
@Getter
@Setter
public class DoctorUpdateInfoDto {
    
    @ApiModelProperty(notes = "Doctor's years of professional experience")
    private int experience;
    
    @ApiModelProperty(notes = "Doctor's consultory address")
    private String consultory;
    
    @ApiModelProperty(notes = "Doctor's gender")
    private String gender;
    
    @ApiModelProperty(notes = "Doctor's home address")
    private String homeAddress;
}
