package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Model to recover user forgotten password")
public class EmailForRecoveryDto {
    
    @ApiModelProperty(notes = "To this email address will be sent the password recovery email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
