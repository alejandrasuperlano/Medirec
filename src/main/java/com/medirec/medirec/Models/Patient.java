package com.medirec.medirec.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PATIENT")
@Getter
@Setter
public class Patient extends User{

    @Column(name = "patientEps")
    @NotBlank
    private String patientEps;

    @Column(name = "patientMaritalStatus")
    @NotBlank
    private String patientMaritalStatus;

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medicalHistoryId", referencedColumnName = "medicalHistoryId")
    private MedicalHistory patientMedicalHistory;

    //Mapped by = nombre del atributo en la entidad donde esta el ManyToOne
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    @Column(name = "patientContacts")
    private List<PatientContact> patientContacts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    @Column(name = "patientAppointments")
    private List<Appointment> patientAppointments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    @Column(name = "patientReviews")
    private List<Score> patientReviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @Column(name = "patientDoctors")
    private List<Access> patientDoctors;

}
