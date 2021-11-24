package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model to complete registration of a doctor")
public class DoctorCompleteRegistrationDto {
    
    @ApiModelProperty(notes = "Doctor's id")
    private int id;

    @ApiModelProperty(notes = "Doctor's home address")
    private String address;
    
    @ApiModelProperty(notes = "Doctor's birthday date in format DD/MM/YYYY", example = "01/10/1980")
    private String birthDay;

    @ApiModelProperty(notes = "Doctor's gender")
    private String gender;

    @ApiModelProperty(notes = "Doctor's consultory address")
    private String consultory;

    @ApiModelProperty(notes = "Doctor's years of professional experience")
    private int experience;

    @ApiModelProperty(notes = "Doctor's university")
    private String university;
}
