package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(description = "Model to complete registration of a patient")
public class PatientCompleteRegistrationDto {

    @ApiModelProperty(notes = "Patient's id")
    private int id;
    
    @ApiModelProperty(notes = "Patient's home address")
    private String address;
    
    @ApiModelProperty(notes = "Doctor's birthday date in format DD/MM/YYYY", example = "01/10/1980")
    private String birthDay;
    
    @ApiModelProperty(notes = "Patient's gender")
    private String gender;
    
    @ApiModelProperty(notes = "Patient's marital status", example = "Single")
    private String maritalStatus;
}
