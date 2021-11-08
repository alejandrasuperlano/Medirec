package com.medirec.medirec.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRegistrationDto {
    private String firstName;
    private String lastName;
    private String docType;
    private String doc;
    private String email;
    private String password;
    private String specialization;
    private String professionalCard;
}
