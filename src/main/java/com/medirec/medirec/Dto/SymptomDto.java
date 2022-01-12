package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "Model for register a symptom")
@Data
public class SymptomDto {
    private String description;
    private String medicine;
    private Date date;


}
