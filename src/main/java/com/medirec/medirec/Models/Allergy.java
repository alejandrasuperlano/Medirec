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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medirec.medirec.Dto.AllergyDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Allergy {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int alergyId;

    @Column()
    @NotBlank
    private String allergen;
    
    @Column()
    @NotBlank
    private String type;


    public Allergy(String allergen, String type) {
        this.allergen = allergen;
        this.type = type;
    }

    public Allergy(AllergyDto dto){
        this.allergen = dto.getAllergen();
        this.type = dto.getType();
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "medicalHistoryId"
    )
    @JsonIgnore
    private MedicalHistory medicalHistory;

}
