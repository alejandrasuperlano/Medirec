package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel(description = "Model to partially register a patient")
public class PatientRegistrationDto {
    
    @ApiModelProperty(notes = "Patient's first name")
    private String firstName;

    @ApiModelProperty(notes = "Patient's last name")
    private String lastName;
    
    @ApiModelProperty(notes = "Patient's identification document type", example = "CC")
    private String docType;
    
    @ApiModelProperty(notes = "Patient's identification document", example = "1134237846")
    private String doc;
    
    @ApiModelProperty(notes = "Patient's email")
    private String email;
    
    @ApiModelProperty(notes = "Patient's password")
    private String password;

    @ApiModelProperty(notes = "Patient's health promoting entity")
    private String eps;
}
