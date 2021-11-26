package com.medirec.medirec.Services;

import java.util.List;

import com.medirec.medirec.Models.Allergy;
import com.medirec.medirec.Repositories.AllergyRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllergyServiceImpl {

    @Autowired
    private AllergyRespository repository;

    public void saveAllergy(Allergy allergy){
        repository.save(allergy);
    }

    public void saveAllergies(List<Allergy> allergies){
        repository.saveAll(allergies);
    }
    
}
