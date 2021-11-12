package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Models.Patient;

public interface PatientService {

    Patient getPatientByEmail(String email);
    boolean passwordConfirm(String password, String confirmPass);
    void passwordRecovery(Patient patient, String newPassword);
    public void createPasswordResetTokenForUser(Patient patient, String token);
    public String validatePasswordResetToken(String token);
}
