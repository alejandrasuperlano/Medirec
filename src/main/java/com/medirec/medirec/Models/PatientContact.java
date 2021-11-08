package com.medirec.medirec.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PatientContact {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private int contactId;

    @Column()
    @NotBlank
    private String contactName;

    @Column()
    @NotBlank
    private String contactLastName;

    @Column()
    @NotBlank
    private String contactPhoneNumber;
    
    @Column()
    @NotBlank
    private String type;

    public PatientContact(String contactName, String contactLastName, String contactPhoneNumber, String type) {
        this.contactName = contactName;
        this.contactLastName = contactLastName;
        this.contactPhoneNumber = contactPhoneNumber;
        this.type = type;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
        name = "patientId",
        referencedColumnName = "userId"
    )
    private Patient patient;
}
