package com.medirec.medirec.Security.Model;

import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class PasswordResetTokenDoctor {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Doctor.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "doctorRecoverId")
    private Doctor doctor;

    private Date expiryDate;

    public PasswordResetTokenDoctor(String token, Doctor doctor) {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.token = token;
        this.doctor = doctor;
    }
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
