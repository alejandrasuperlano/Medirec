package com.medirec.medirec.Models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Symptom {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int symptomId;

    @Column(
        nullable = false
    )
    private String description;

    @Column(
        nullable = false
    )
    private LocalDate date;

    @ManyToOne()
    @JoinColumn(
        name = "medicalHistoryId",
        referencedColumnName = "medicalHistoryId"
    )
    private MedicalHistory medicalHistory;

    public Symptom(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }

}
