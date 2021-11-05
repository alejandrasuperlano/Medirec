package com.medirec.medirec.Models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int appointmentId;

    @Column()
    @NotBlank
    private String reason;
    
    @Column()
    @NotNull
    private Date appointmentDate;
    
    @Column()
    @NotBlank
    private String physicalExamination;
    
    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalHistoryId")
    private MedicalHistory medicalHistory;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId")
    private Doctor doctor;

    public Appointment(String reason, Date appointmentDate, String physicalExamination) {
        this.reason = reason;
        this.appointmentDate = appointmentDate;
        this.physicalExamination = physicalExamination;
    }

}
