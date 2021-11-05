package com.medirec.medirec.Models;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class MedicalHistory {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long medicalHistoryId;

    // -----------RELATIONSHIPS -------------//
    @OneToOne(mappedBy = "patientMedicalHistoryId")
    private Patient patient;
    // TODO: Implementar el resto de atributos (listas)
}
