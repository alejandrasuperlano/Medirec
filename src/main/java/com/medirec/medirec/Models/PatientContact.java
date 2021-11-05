package com.medirec.medirec.Models;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class PatientContact {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Long contactId;

    @Column(
        nullable = false
    )
    private String contactName;

    @Column(
        nullable = false
    )
    private String contactLastName;

    @Column(
        nullable = false
    )
    private String contactPhoneNumber;
    
    @Column(
        nullable = false
    )
    private String type;

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient")
    private Patient patient;

    public PatientContact(String contactName, String contactLastName, String contactPhoneNumber, String type) {
        this.contactName = contactName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.type = type;
    }

    
}
