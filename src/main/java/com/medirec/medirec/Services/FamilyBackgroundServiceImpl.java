package com.medirec.medirec.Services;

import java.util.List;

import com.medirec.medirec.Models.FamilyBackground;
import com.medirec.medirec.Repositories.FamilyBackgroundRepository;
import com.medirec.medirec.Services.Interfaces.FamilyBackgroundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyBackgroundServiceImpl  implements FamilyBackgroundService{
    
    @Autowired
    private FamilyBackgroundRepository repository;

    public void saveBackground(FamilyBackground background){
        repository.save(background);
    }

    public void saveBackgrounds(List<FamilyBackground> backgrounds){
        repository.saveAll(backgrounds);
    }
}
