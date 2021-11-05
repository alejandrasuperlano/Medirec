package com.medirec.medirec.Models;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long scoreId;

    @Column(
        nullable = false
    )
    private double score;

    @Column(
        nullable = false
    )
    private String opinion;

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorScore")
    private Doctor doctorScore;

    public Score(double score, String opinion) {
        this.score = score;
        this.opinion = opinion;
    }

}
