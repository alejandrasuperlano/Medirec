package com.medirec.medirec.Models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

import java.util.ArrayList;
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
    private String doctorUniversity;

    @Column(name = "doctorExperience")
    private int doctorExperience;

    @Column(name = "doctorConsultory")
    private String doctorConsultory;

    @Column(name = "doctorProfessionalCard")
    @NotBlank
    private String doctorProfessionalCard;

    // Register constructor
    public Doctor(String userFirstName, String userLastName, String userDocType, 
                String userDoc, String userEmail, String userPassword, String doctorSpecialization, String doctorProfessionalCard) {
        
        super(userFirstName, userLastName, userDocType, userDoc, userEmail, userPassword);
        this.doctorSpecialization = doctorSpecialization;
        this.doctorProfessionalCard = doctorProfessionalCard;

        this.doctorAppointments = new ArrayList<>();
        this.doctorScores = new ArrayList<>();
        this.doctorPatients = new ArrayList<>();
    
    }

    public Doctor(){
        super();
    }


    /*@Transient
    private int role;*/

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private List<Role> roles;

}
