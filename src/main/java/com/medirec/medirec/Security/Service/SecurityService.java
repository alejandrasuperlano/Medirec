package com.medirec.medirec.Security.Service;

import com.medirec.medirec.Repositories.PasswordTokenDoctorRepository;
import com.medirec.medirec.Repositories.PasswordTokenPatientRepository;
import com.medirec.medirec.Security.Model.PasswordResetTokenPatient;
import com.medirec.medirec.Security.Model.PasswordResetTokenDoctor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

public class SecurityService {

    @Autowired
    PasswordTokenPatientRepository passwordTokenPatientRepository;

    @Autowired
    PasswordTokenDoctorRepository passwordTokenDoctorRepository;

    public String validatePasswordResetToken(String token) {
        final PasswordResetTokenPatient passToken = passwordTokenPatientRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    public String validatePasswordResetTokenDoctor(String token) {
        final PasswordResetTokenDoctor passToken = passwordTokenDoctorRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetTokenPatient passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetTokenPatient passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    private boolean isTokenFound(PasswordResetTokenDoctor passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetTokenDoctor passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
