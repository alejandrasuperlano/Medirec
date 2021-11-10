package com.medirec.medirec.Models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Document {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int documentId;
    
    private String documentName;
    private String documentType;
    
    @Lob
    private byte[] data;

    public Document(String documentName, String documentType, byte[] data) {
        this.documentName = documentName;
        this.documentType = documentType;
        this.data = data;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "medicalHistoryId",
        referencedColumnName = "medicalHistoryId"
    )
    private MedicalHistory medicalHistory;
}
