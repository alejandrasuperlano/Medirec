package com.medirec.medirec.Services;

import java.util.Optional;

import com.medirec.medirec.Models.MedicalHistory;
import com.medirec.medirec.Repositories.MedicalHistoryRepository;
import com.medirec.medirec.Services.Interfaces.MedicalHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService{
    
    @Autowired
    private MedicalHistoryRepository repository;

    public Optional<MedicalHistory> getMedicalHistoryById(int id){
        return repository.findById(id);
    }
}
