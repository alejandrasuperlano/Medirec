package com.medirec.medirec.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatientCompleteRegistrationDto {

    private int id;
    private String address;
    private String birthDay;
    private String gender;
    private String maritalStatus;
}
