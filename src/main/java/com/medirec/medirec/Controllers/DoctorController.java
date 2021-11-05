package com.medirec.medirec.Controllers;

import com.medirec.medirec.Services.Interfaces.DoctorService;
import com.medirec.medirec.Services.Interfaces.PatientService;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    UserService userService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    ModelMapper modelMapper;
}
