package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Symptom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRepository extends JpaRepository<Symptom, Long>{
    
}
