package com.medirec.medirec.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medirec.medirec.Dto.IllnessDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "ILLNESS")
@Getter
@Setter
@NoArgsConstructor
public class Illness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int illnessId;

    @Column(name = "illnessName")
    @NotBlank
    private String illnessName;
    @Column(name = "detectionDate")
    @NotNull
    private Date detectionDate;
    @Column(name = "illnessDescription")
    private String illnessDescription;

    public Illness(String illnessName, Date detectionDate, String illnessDescription) {
        this.illnessName = illnessName;
        this.detectionDate = detectionDate;
        this.illnessDescription = illnessDescription;
    }

    public Illness(IllnessDto dto) throws IllegalStateException{
        this.illnessName = dto.getIllnessName();
        this.illnessDescription = dto.getIllnessDescription();
        Date detectionDate;
        try{
            detectionDate = new SimpleDateFormat("dd/MM/yyyy").parse(dto.getDetectionDate());  
        }catch (ParseException e){
            throw new IllegalStateException("Error parsing detection date");
        }
        this.detectionDate = detectionDate;
    }

    // ------------------------------- RELATIONSHIPS ------------------------------- //
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalHistoryId")
    @JsonIgnore
    private MedicalHistory medicalHistory;
}
