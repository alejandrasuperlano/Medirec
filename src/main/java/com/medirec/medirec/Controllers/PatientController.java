package com.medirec.medirec.Controllers;

import com.medirec.medirec.Services.Interfaces.PatientService;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Autowired
    ModelMapper modelMapper;
}
