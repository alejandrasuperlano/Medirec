package com.medirec.medirec.Models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int medicalHistoryId;

    // @JsonIgnore
    @OneToMany(mappedBy = "medicalHistory")
    private List<Symptom> symptoms;
    
    public MedicalHistory() {
    }


    // TODO: Implementar el resto de atributos (listas)
}
