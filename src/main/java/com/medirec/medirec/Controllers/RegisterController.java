package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.PatientRegistrationDto;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Services.DoctorServiceImpl;
import com.medirec.medirec.Services.PatientServiceImpl;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import com.medirec.medirec.Services.Interfaces.PatientService;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path = "patient")
    public String test(){
        return "this is a test";
    }

    @PostMapping(path = "patient")
    public void registerPatient(@RequestBody PatientRegistrationDto patientDto){
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
        
        patientService.registerPatient(patient);
    }
}
