package com.medirec.medirec.Dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model for medical history creation")
@Getter
@Setter
public class MedicalhistoryCreationDto {

    @ApiModelProperty(notes = "List of allergy models")
    List<AllergyDto> allergies;
    
    @ApiModelProperty(notes = "List of illness models")
    List<IllnessDto> illnesses;
    
    @ApiModelProperty(notes = "List of personal record models")
    List<PersonalRecordDto> personalRecords;
    
    @ApiModelProperty(notes = "List of family background models")
    List<FamilyBackgroundDto> familyBackgrounds;
}
