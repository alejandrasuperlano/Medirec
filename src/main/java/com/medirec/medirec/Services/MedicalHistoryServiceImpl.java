package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.MedicalHistoryRepository;
import com.medirec.medirec.Services.Interfaces.MedicalHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService{
    
    @Autowired
    private MedicalHistoryRepository repository;
}
