package com.medirec.medirec.Repositories;

import java.util.Optional;

import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.PatientContact;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PatientContactRepository extends CrudRepository<PatientContact, Integer>{
    
    // public Optional<Patient> findBy
}
