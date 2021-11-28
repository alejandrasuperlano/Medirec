package com.medirec.medirec.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ACCESS")
@Data
@Embeddable
public class Access {
    @EmbeddedId
    private AccessPK id;

    @Column(name = "permission")
    // 1 = yes, 0 = no
    private int permission;

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient")
    private Patient patient;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    public AccessPK getId() {
        return id;
    }

    public void setId(AccessPK id) {
        this.id = id;
    }
}
