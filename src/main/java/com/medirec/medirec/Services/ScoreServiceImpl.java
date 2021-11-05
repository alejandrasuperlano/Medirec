package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.ScoreRepository;
import com.medirec.medirec.Services.Interfaces.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService{
    
    @Autowired
    private ScoreRepository repository;
}
