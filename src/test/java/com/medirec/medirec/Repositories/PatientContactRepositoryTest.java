package com.medirec.medirec.Repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.medirec.medirec.Models.PatientContact;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PatientContactRepositoryTest {

    @Autowired
    private PatientContactRepository repository;

    @Test
    public void addPatientContact(){
        PatientContact patientContact = new PatientContact("Juan", "Perez", "3213113925", "Acompañante");

        repository.save(patientContact);
    }
}