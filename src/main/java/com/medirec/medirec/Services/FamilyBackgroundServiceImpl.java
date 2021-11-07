package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.FamilyBackgroundRepository;
import com.medirec.medirec.Services.Interfaces.FamilyBackgroundService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyBackgroundServiceImpl  implements FamilyBackgroundService{
    
    @Autowired
    private FamilyBackgroundRepository repository;
}
