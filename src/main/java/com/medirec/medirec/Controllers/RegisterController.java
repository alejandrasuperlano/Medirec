package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.DoctorCompleteRegistrationDto;
import com.medirec.medirec.Dto.DoctorRegistrationDto;
import com.medirec.medirec.Dto.PatientCompleteRegistrationDto;
import com.medirec.medirec.Dto.PatientRegistrationDto;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Services.DoctorServiceImpl;
import com.medirec.medirec.Services.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping(path = "patient")
    public ResponseEntity<String> registerPatient(@RequestBody PatientRegistrationDto patientDto){
        Patient patient = modelMapper.typeMap(PatientRegistrationDto.class, Patient.class).addMappings(mapper ->{
            mapper.map(src -> src.getFirstName(),
            Patient::setUserFirstName);
            mapper.map(src -> src.getLastName(),
            Patient::setUserLastName);
            mapper.map(src -> src.getDocType(),
            Patient::setUserDocType);
            mapper.map(src -> src.getDoc(),
            Patient::setUserDoc);
            mapper.map(src -> src.getEmail(),
            Patient::setUserEmail);
            mapper.map(src -> src.getPassword(),
            Patient::setUserPassword);
            mapper.map(src -> src.getEps(),
            Patient::setPatientEps);
        }).map(patientDto);

        try {
            patientService.registerPatient(patient);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Patient registered succesfully", HttpStatus.OK);
    }
    
    @PostMapping(path = "doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorRegistrationDto doctorDto){
        Doctor doctor = modelMapper.typeMap(DoctorRegistrationDto.class, Doctor.class).addMappings(mapper ->{
            mapper.map(src -> src.getFirstName(),
            Doctor::setUserFirstName);
            mapper.map(src -> src.getLastName(),
            Doctor::setUserLastName);
            mapper.map(src -> src.getDocType(),
            Doctor::setUserDocType);
            mapper.map(src -> src.getDoc(),
            Doctor::setUserDoc);
            mapper.map(src -> src.getEmail(),
            Doctor::setUserEmail);
            mapper.map(src -> src.getPassword(),
            Doctor::setUserPassword);
            mapper.map(src -> src.getSpecialization(),
            Doctor::setDoctorSpecialization);
            mapper.map(src -> src.getProfessionalCard(),
            Doctor::setDoctorProfessionalCard);
        }).map(doctorDto);
        
        try {
            doctorService.registerDoctor(doctor);
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("Doctor registred succesfully", HttpStatus.OK);
    }

    @PostMapping(path = "patient/complete")
    public ResponseEntity<String> completePatientRegistration(@RequestBody PatientCompleteRegistrationDto patientDto){
        
        try {
            patientService.completeRegistration(patientDto);
        } catch (IllegalStateException e) {
            if(e.getMessage() == "No patient with such id"){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }else if(e.getMessage() == "Error parsing birthday date"){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<String>("Patient complete registration succesfully", HttpStatus.OK);
    }
    
    @PostMapping(path = "doctor/complete")
    public ResponseEntity<String> completePatientRegistration(@RequestBody DoctorCompleteRegistrationDto doctorDto){
        try {
            doctorService.completeRegistration(doctorDto);
            
        } catch (IllegalStateException e) {
            if(e.getMessage() == "No doctor with such id"){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }else if(e.getMessage() == "Error parsing birthday date"){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<String>("Doctor complete registration succesfully", HttpStatus.OK);
    }
}
