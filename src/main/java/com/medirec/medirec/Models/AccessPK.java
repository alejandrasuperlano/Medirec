package com.medirec.medirec.Models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class AccessPK implements Serializable {
    @Basic
    private int patientId;

    @Basic
    private int doctorId;

    public AccessPK(int patientId, int doctorId) {
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

}
