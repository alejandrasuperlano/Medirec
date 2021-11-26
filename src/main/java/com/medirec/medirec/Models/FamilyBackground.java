package com.medirec.medirec.Models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class FamilyBackground {

    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int familyBackgroundId;

    @Column()
    @NotBlank
    private String familyMember;
    
    @Column()
    @NotBlank
    private String description;

    public FamilyBackground(String familyMember, String description) {
        this.familyMember = familyMember;
        this.description = description;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "medicalHistoryId"
    )
    private MedicalHistory medicalHistory;
}
