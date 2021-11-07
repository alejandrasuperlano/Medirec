package com.medirec.medirec.Controllers;

import com.medirec.medirec.Services.Interfaces.DoctorService;
import com.medirec.medirec.Services.Interfaces.PatientService;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public String test(){
        return "this is a test";
    }
}
