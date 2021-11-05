package com.medirec.medirec.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int scoreId;

    @Column()
    @NotNull
    private double score;

    @Column()
    @NotBlank
    private String opinion;


    public Score(double score, String opinion, Patient patient, Doctor doctor) {
        this.score = score;
        this.opinion = opinion;
        this.patient = patient;
        this.doctor = doctor;
    }

    
    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "patientId",
        referencedColumnName = "userId"
        )
        private Patient patient;
        

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "doctorId",
        referencedColumnName = "userId"
    )
    private Doctor doctor;


}
