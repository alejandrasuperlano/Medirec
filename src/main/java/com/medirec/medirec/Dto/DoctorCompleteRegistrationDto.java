package com.medirec.medirec.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorCompleteRegistrationDto {
    private int id;
    private String address;
    private String birthDay;
    private String gender;
    private String consultory;
    private int experience;
    private String university;
}
