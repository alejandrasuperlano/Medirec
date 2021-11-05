package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.PatientContactRepository;
import com.medirec.medirec.Services.Interfaces.PatientContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientContactServiceImpl implements PatientContactService{
    
    @Autowired
    private PatientContactRepository repository;
}
