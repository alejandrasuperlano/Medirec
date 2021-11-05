package com.medirec.medirec.Models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Symptom {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int symptomId;

    @Column()
    @NotBlank
    private String description;

    @Column()
    @NotNull
    private Date date;

    public Symptom(String description, Date date) {
        this.description = description;
        this.date = date;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //

    @ManyToOne()
    @JoinColumn(
        name = "medicalHistoryId",
        referencedColumnName = "medicalHistoryId"
    )
    private MedicalHistory medicalHistory;
}
