package com.medirec.medirec.Repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.Score;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScoreRepositoryTest {

    @Autowired
    private ScoreRepository repository;

    @Test
    public void saveScore(){
        // Patient patient = new Patient();

        // repository.save(score);
    }


}