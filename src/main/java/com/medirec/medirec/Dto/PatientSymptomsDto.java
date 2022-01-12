package com.medirec.medirec.Dto;

import java.util.List;

import com.medirec.medirec.Models.Symptom;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model for list of patient's symptoms")
@Getter
@Setter
public class PatientSymptomsDto {
    
    @ApiModelProperty(notes = "Patient's full name")
    private String patientName;
    
    @ApiModelProperty(notes = "Patient's list of symptoms")
    private List<Symptom> symptoms;
}
