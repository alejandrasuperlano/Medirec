package com.medirec.medirec.Controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.medirec.medirec.Dto.AllergyDto;
import com.medirec.medirec.Dto.FamilyBackgroundDto;
import com.medirec.medirec.Dto.IllnessDto;
import com.medirec.medirec.Dto.MedicalhistoryCreationDto;
import com.medirec.medirec.Dto.PersonalRecordDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Allergy;
import com.medirec.medirec.Models.FamilyBackground;
import com.medirec.medirec.Models.Illness;
import com.medirec.medirec.Models.MedicalHistory;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.PersonalRecord;
import com.medirec.medirec.Security.JWT.JwtProvider;
import com.medirec.medirec.Services.AllergyServiceImpl;
import com.medirec.medirec.Services.FamilyBackgroundServiceImpl;
import com.medirec.medirec.Services.IllnessServiceImpl;
import com.medirec.medirec.Services.MedicalHistoryServiceImpl;
import com.medirec.medirec.Services.PatientServiceImpl;
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
    PatientServiceImpl patientService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping(path = "{patientId}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value = "Patient's medical history creation")
    public ResponseEntity<String> creation(
        @PathVariable("patientId") int patientId,
        @RequestBody MedicalhistoryCreationDto medicalHistoryDto,
        @RequestParam("sessionToken") String sessionToken
    ){  
        if(!jwtProvider.tokenValidation(sessionToken)){
            return new ResponseEntity<String>("Invalid session token", HttpStatus.BAD_REQUEST);
        }

        Patient patient = patientService.getPatientById(patientId);
        if(patient == null){
            return new ResponseEntity<String>("No patient with such id", HttpStatus.BAD_REQUEST);
        }
        
        MedicalHistory medicalHistory = patient.getPatientMedicalHistory();

        List<Allergy> allergies = new ArrayList<>();
        List<Illness> illnesses = new ArrayList<>();
        List<PersonalRecord> records = new ArrayList<>();
        List<FamilyBackground> backgrounds = new ArrayList<>();
        
        for(AllergyDto allergyDto : medicalHistoryDto.getAllergies()){
            Allergy allergy = new Allergy(allergyDto);            
            allergy.setMedicalHistory(medicalHistory);
            
            allergies.add(allergy);
        }
        
        for(IllnessDto illnessDto : medicalHistoryDto.getIllnesses()){
            Illness illness;
            try {
                illness = new Illness(illnessDto);
            } catch (IllegalStateException e) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            illness.setMedicalHistory(medicalHistory);
            
            illnesses.add(illness);
        }
        
        for(PersonalRecordDto prDto : medicalHistoryDto.getPersonalRecords()){
            PersonalRecord record;
            try {
                record = new PersonalRecord(prDto);
            } catch (IllegalStateException e) {
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
            record.setMedicalHistory(medicalHistory);
            
            records.add(record);
        }

        for(FamilyBackgroundDto backgroundDto : medicalHistoryDto.getFamilyBackgrounds()){
            FamilyBackground background = new FamilyBackground(backgroundDto);
            background.setMedicalHistory(medicalHistory);
            backgrounds.add(background);
        }

        allergyService.saveAllergies(allergies);
        illnessService.saveIllnesses(illnesses);
        personalRecordService.savePersonalRecords(records);
        familyBackgroundService.saveBackgrounds(backgrounds);
        
        return new ResponseEntity<String>("Medical history created succesfully", HttpStatus.OK);
    }

    @GetMapping(path = "{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetch patient's medical history")
    public ResponseEntity<Response> getMedicalHistory(
        @PathVariable("patientId") int patientId,
        @RequestParam("sessionToken") String sessionToken
    ){
        if(!jwtProvider.tokenValidation(sessionToken)){
            Response tokenResponse = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                "Invalid session token",
                null
            );
            
            return new ResponseEntity<Response>(tokenResponse, HttpStatus.BAD_REQUEST);
        }
        
        MedicalHistory medicalHistory;
        Response response;
        
        try {
            medicalHistory = medicalHistoryService.getMedicalHistory(patientId);
        } catch (IllegalStateException e){
            response = new Response(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                null
            );
            
            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }
        
        response = new Response(
            HttpStatus.OK.toString(),
            null,
            medicalHistory
            );
            
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    
}
