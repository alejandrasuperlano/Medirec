package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.SymptomRepository;
import com.medirec.medirec.Services.Interfaces.SymptomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SymptomServiceImpl implements SymptomService{
    
    @Autowired
    private SymptomRepository repository;
}
