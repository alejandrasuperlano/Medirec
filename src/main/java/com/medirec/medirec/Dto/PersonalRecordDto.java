package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Model for personal record")
@Getter
@Setter
public class PersonalRecordDto {

    @ApiModelProperty(notes = "Date in format DD/MM/YYYY", example = "01/15/2010")
    private String date;

    @ApiModelProperty(notes = "Personal record description")
    private String description;
}
