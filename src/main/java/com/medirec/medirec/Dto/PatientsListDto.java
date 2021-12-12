package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Model for each patient on the lists of patients")
public class PatientsListDto {
    private String firstName;
    private String lastName;
    private String gender;
    private String doc;
    private int id;
}
