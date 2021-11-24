package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model to partially register a doctor")
public class DoctorRegistrationDto {
    @ApiModelProperty(notes = "Doctor's first name")
    private String firstName;
    
    @ApiModelProperty(notes = "Doctor's last name")
    private String lastName;
    
    @ApiModelProperty(notes = "Doctor's identification document type", example = "CC")
    private String docType;
    
    @ApiModelProperty(notes = "Doctor's identification document", example = "1134237846")
    private String doc;
    
    @ApiModelProperty(notes = "Doctor's email")
    private String email;
    
    @ApiModelProperty(notes = "Doctor's password")
    private String password;
    
    @ApiModelProperty(notes = "Doctor's professional specialization", example = "Pediatry")
    private String specialization;
    
    @ApiModelProperty(notes = "Doctor's professional card number")
    private String professionalCard;
}
