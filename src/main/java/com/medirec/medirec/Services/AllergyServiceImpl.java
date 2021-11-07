package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.AllergyRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllergyServiceImpl {

    @Autowired
    private AllergyRespository respository;
    
}
