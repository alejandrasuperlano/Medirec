package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.PatientContact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientContactRepository extends JpaRepository<PatientContact, Integer>{
    
}
