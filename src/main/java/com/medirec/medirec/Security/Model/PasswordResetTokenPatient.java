package com.medirec.medirec.Security.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medirec.medirec.Models.Patient;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class PasswordResetTokenPatient {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Patient.class, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(nullable = false, name = "patientRecoverId")
    private Patient patient;

/*    @OneToOne(targetEntity = Doctor.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "doctorRecoverId")
    private Doctor doctor;*/

    private Date expiryDate;

    public PasswordResetTokenPatient(String token, Patient patient) {
        this.expiryDate = calculateExpiryDate(EXPIRATION);
        this.token = token;
        this.patient = patient;
    }

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

/*
    public PasswordResetToken(String token, Doctor doctor) {
        this.token = token;
        this.doctor = doctor;
    }*/

}
