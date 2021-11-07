package com.medirec.medirec.Services;

import com.medirec.medirec.Models.Doctor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DoctorServiceImplTest {
    @Autowired
    private DoctorServiceImpl service;

    @Test
    public void registerDoctor(){

        Doctor doctor = new Doctor("Juan", "Gomez", "CC", "11983478628", "juan@gmail.com",
                            "1453", "Cardiologia", "11122334");

        service.registerDoctor(doctor);
    }

    @Test
    public void completeRegistration(){
        service.completeRegistration(2, "Calle 11 #123", "20/04/1995", "M", "Carrera 45 #50-50",
                                    10, "UNAL");
    }
}