package com.medirec.medirec.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

@Entity
@Table(name = "PATIENT")
@Getter
@Setter
public class Patient extends User{

    @Column(name = "patientEps")
    @NotBlank
    private String patientEps;

    @Column(name = "patientMaritalStatus")
    private String patientMaritalStatus;

    // Register constructor
    public Patient(String userFirstName, String userLastName, String userDocType, 
                    String userDoc, String userEmail, String userPassword, String patientEps) {

        super(userFirstName, userLastName, userDocType, userDoc, userEmail, userPassword);
        this.patientEps = patientEps;

        this.patientAppointments = new ArrayList<>();
        this.patientContacts = new ArrayList<>();
        this.patientReviews = new ArrayList<>();
        this.patientDoctors = new ArrayList<>();
        this.roles = new ArrayList<>();
        
        this.patientMedicalHistory = new MedicalHistory();
        
    }
    
    public Patient(){
        super();
        
        this.patientAppointments = new ArrayList<>();
        this.patientContacts = new ArrayList<>();
        this.patientReviews = new ArrayList<>();
        this.patientDoctors = new ArrayList<>();
        this.roles = new ArrayList<>();

        this.patientMedicalHistory = new MedicalHistory();
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    /*@Transient
        private int role;*/
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(
            nullable = true,
            name = "patientId"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "roleId"
        )
    )
    private List<Role> roles;

}
