package com.medirec.medirec.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatientRegistrationDto {
    
    private String firstName;
    private String lastName;
    private String docType;
    private String doc;
    private String email;
    private String password;
    private String eps;
}
