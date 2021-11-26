package com.medirec.medirec.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.medirec.medirec.Dto.PersonalRecordDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "PERSONALRECORD")
@Getter
@Setter
public class PersonalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prId;
    @Column(name = "date")
    @NotNull
    private Date date;
    @Column(name = "prDescription")
    private String prDescription;

    public PersonalRecord(Date date, String prDescription) {
        this.date = date;
        this.prDescription = prDescription;
    }

    public PersonalRecord(PersonalRecordDto dto) throws IllegalStateException{
        this.prDescription = dto.getDescription();
        Date date;
        try{
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDate());  
        }catch (ParseException e){
            throw new IllegalStateException("Error parsing personal record date");
        }
        this.date = date;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalHistoryId")
    private MedicalHistory medicalHistory;

}
