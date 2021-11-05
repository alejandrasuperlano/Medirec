package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.MedicalHistory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class MedicalHistoryRepositoryTest {

    @Autowired
    private MedicalHistoryRepository repository;

    @Test
    public void addMedicalHistory(){
        MedicalHistory medicalHistory = new MedicalHistory();

        repository.save(medicalHistory);
    }
}