package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.IllnessRepository;
import com.medirec.medirec.Services.Interfaces.IllnessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IllnessServiceImpl implements IllnessService{

    @Autowired
    IllnessRepository repository;
    
}
