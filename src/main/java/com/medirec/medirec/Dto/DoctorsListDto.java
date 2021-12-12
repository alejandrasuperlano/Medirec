package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Model for each doctor on the lists of doctors")
public class DoctorsListDto {
    private String firstName;
    private String lastName;
    private String specialization;
    private int id;
}
