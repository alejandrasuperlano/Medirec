package com.medirec.medirec.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MedicalHistory {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int medicalHistoryId;
    
    
    // -----------RELATIONSHIPS -------------//
    @OneToOne(mappedBy = "patientMedicalHistory")
    private Patient patient;

    // @JsonIgnore
    @OneToMany(mappedBy = "medicalHistory")
    private List<Symptom> symptoms;
    
    @OneToMany(mappedBy = "medicalHistory")
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "medicalHistory")
    private List<FamilyBackground> familyBackgrounds;
    
    @OneToMany(mappedBy = "medicalHistory")
    private List<Allergy> allergies;
}
