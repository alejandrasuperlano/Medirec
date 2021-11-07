package com.medirec.medirec.Models;

import java.util.List;

import javax.persistence.*;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicalHistory")
    @Column(name = "personalRecords")
    private List<PersonalRecord> personalRecords;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medicalHistory")
    @Column(name = "illnesses")
    private List<Illness> illnesses;
    // TODO: Implementar el resto de atributos (listas)
}
