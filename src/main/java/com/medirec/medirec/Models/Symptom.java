package com.medirec.medirec.Models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Symptom {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long symptomId;

    @Column(
        nullable = false
    )
    private String description;

    @Column(
        nullable = false
    )
    private LocalDate date;


    public Symptom(String description, LocalDate date) {
        this.description = description;
        this.date = date;
    }

}
