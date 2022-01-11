package com.medirec.medirec.Dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Model to send or update a score of a doctor")
public class ScoreDto {
    
    @ApiModelProperty(notes = "Score's id, if updating, must be not null")
    private int scoreId;

    @ApiModelProperty(notes = "Doctor's score")
    private double score;
    
    @ApiModelProperty(notes = "Doctor's opinion")
    private String opinion;
}
