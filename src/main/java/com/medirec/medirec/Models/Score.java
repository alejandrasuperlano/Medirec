package com.medirec.medirec.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public Score(double score, String opinion) {
        this.score = score;
        this.opinion = opinion;
    }

}
