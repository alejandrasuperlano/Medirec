package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Models.Patient;

public interface PatientService {

    Patient getPatientByEmail(String email);
}
