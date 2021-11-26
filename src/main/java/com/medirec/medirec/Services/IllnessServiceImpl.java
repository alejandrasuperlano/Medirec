package com.medirec.medirec.Services;

import java.util.List;

import com.medirec.medirec.Models.Illness;
import com.medirec.medirec.Repositories.IllnessRepository;
import com.medirec.medirec.Services.Interfaces.IllnessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IllnessServiceImpl implements IllnessService{

    @Autowired
    IllnessRepository repository;

    public void saveIllness(Illness illness){
        repository.save(illness);
    }

    public void saveIllnesses(List<Illness> illnesses){
        repository.saveAll(illnesses);
    }
    
}
