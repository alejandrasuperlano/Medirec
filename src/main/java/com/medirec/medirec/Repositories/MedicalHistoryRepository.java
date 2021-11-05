package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.MedicalHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer>{
    
}
