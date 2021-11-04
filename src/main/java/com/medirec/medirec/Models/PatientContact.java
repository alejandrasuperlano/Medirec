package com.medirec.medirec.Models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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


    public PatientContact(String contactName, String contactLastName, String contactPhoneNumber, String type) {
        this.contactName = contactName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.type = type;
    }

    
}
