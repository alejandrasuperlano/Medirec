package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;

public interface DoctorService {

    Doctor getDoctorByEmail (String email);
    boolean passwordConfirm(String password, String passwordConfirmed);
    void passwordRecovery (Doctor doctorDB, String newPassword);
    public void createPasswordResetTokenForUser(Doctor doctor, String token);
    public String validatePasswordResetToken(String token);
}
