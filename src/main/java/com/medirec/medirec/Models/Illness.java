package com.medirec.medirec.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "ILLNESS")
public class Illness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int illnessId;

    @Column(name = "illnessName")
    @NotBlank
    private String illnessName;
    @Column(name = "detectionDate")
    @NotNull
    private Date detectionDate;
    @Column(name = "illnessDescription")
    private String illnessDescription;

    public Illness(String illnessName, Date detectionDate, String illnessDescription) {
        this.illnessName = illnessName;
        this.detectionDate = detectionDate;
        this.illnessDescription = illnessDescription;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalHistoryId")
    private MedicalHistory medicalHistory;
}
