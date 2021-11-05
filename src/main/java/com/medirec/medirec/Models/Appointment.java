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
public class Appointment {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int appointmentId;

    @Column(
        nullable = false
    )
    private String reason;

    @Column(
        nullable = false
    )
    private LocalDate appointmentDate;
    
    @Column(
        nullable = false
    )
    private String physicalExamination;

    public Appointment(String reason, LocalDate appointmentDate, String physicalExamination) {
        this.reason = reason;
        this.appointmentDate = appointmentDate;
        this.physicalExamination = physicalExamination;
    }

}
