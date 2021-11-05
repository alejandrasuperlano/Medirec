package com.medirec.medirec.Repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;

import com.medirec.medirec.Models.Symptom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SymptomRepositoryTest {
    @Autowired
    private SymptomRepository repository;

    // @Test
    // public void addSymptom(){
        
    //     Symptom symptom = new Symptom("Dolor de muela", D);

    //     repository.save(symptom);
    // }
}