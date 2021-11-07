package com.medirec.medirec.Services;

import com.medirec.medirec.Models.Patient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PatientServiceImplTest {

    @Autowired
    private PatientServiceImpl service;

    @Test
    public void registerPatient(){

        Patient patient = new Patient("Miguel", "Perez", "CC", "1192713147",
                                    "miguel@gmail.com", "1234", "Sanitas");

        service.registerPatient(patient);
    }

    @Test
    public void completeRegistration(){

        service.completeRegistration(11, "Calle 45 #50-49", "10/01/2001", "M", "Soltero");
    }
}