package com.medirec.medirec.Models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "PERSONALRECORD")
@Data
public class PersonalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prId;
    @Column(name = "date")
    @NotNull
    private Date date;
    @Column(name = "prDescription")
    private String prDescription;

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalHistoryId")
    private MedicalHistory medicalHistory;

}
