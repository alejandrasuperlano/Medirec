package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = ("DTO model for accessing to a Patient profile"))
@Data
public class ProfileRequestDto {
    private int patientId;
}
