package com.medirec.medirec.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medirec.medirec.Dto.ScoreDto;

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

    public Score(ScoreDto dto){
        this.score = dto.getScore();
        this.opinion = dto.getOpinion();
    }

    
    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "patientId",
        referencedColumnName = "userId"
        )
    @JsonIgnore
    private Patient patient;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "doctorId",
        referencedColumnName = "userId"
        )
    @JsonIgnore
    private Doctor doctor;


}
