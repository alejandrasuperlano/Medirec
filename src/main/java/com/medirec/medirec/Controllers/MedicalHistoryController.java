package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.MedicalhistoryCreationDto;
import com.medirec.medirec.Services.AllergyServiceImpl;
import com.medirec.medirec.Services.FamilyBackgroundServiceImpl;
import com.medirec.medirec.Services.IllnessServiceImpl;
import com.medirec.medirec.Services.MedicalHistoryServiceImpl;
import com.medirec.medirec.Services.PersonalRecordServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/patient/medicalHistory")
@Api(tags = "Patient's medical history", description = "CRUD operations for patient's medical history")
public class MedicalHistoryController {
    
    @Autowired
    MedicalHistoryServiceImpl medicalHistoryService;

    @Autowired
    AllergyServiceImpl allergyService;

    @Autowired
    FamilyBackgroundServiceImpl familyBackgroundService;

    @Autowired
    PersonalRecordServiceImpl personalRecordService;

    @Autowired
    IllnessServiceImpl illnessService;

    @Autowired
    ModelMapper modelMapper;

    // @PostMapping(path = "{patientId}", produces = MediaType.TEXT_PLAIN_VALUE)
    // @ApiOperation(value = "Patient's medical history creation")
    // public ResponseEntity<String> creation(
    //     @PathVariable("patientId") int patientId,
    //     @RequestBody MedicalhistoryCreationDto medicalhistoryDto
    // ){
        
    // }
    
}
