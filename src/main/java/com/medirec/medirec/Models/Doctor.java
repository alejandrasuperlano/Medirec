package com.medirec.medirec.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOCTOR", uniqueConstraints = @UniqueConstraint(columnNames = "doctorProfessionalCard"))
@Getter
@Setter
public class Doctor extends User{
    @Column(name = "doctorSpecialization")
    @NotBlank
    private String doctorSpecialization;

    @Column(name = "doctorUniversity")
    @NotBlank
    private String doctorUniversity;

    @Column(name = "doctorExperience")
    @NotNull
    private int doctorExperience;

    @Column(name = "doctorConsultory")
    @NotBlank
    private String doctorConsultory;

    @Column(name = "doctorProfessionalCard")
    @NotBlank
    private String doctorProfessionalCard;

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @Column(name = "doctorScores")
    private List<Score> doctorScores;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @Column(name = "doctorAppointments")
    private List<Appointment> doctorAppointments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    @Column(name = "doctorPatients")
    private List<Access> doctorPatients;
}
