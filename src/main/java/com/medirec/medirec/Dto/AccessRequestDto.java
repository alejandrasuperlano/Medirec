package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;

@ApiModel("DTO model for accessing to a Patient profile")
public class AccessRequestDto {
    private int doctorId;
    private int patientId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
