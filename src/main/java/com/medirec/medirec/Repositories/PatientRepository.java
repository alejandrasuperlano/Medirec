package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    Optional<Patient> findPatientByUserEmail (String email);
    Optional<Patient> findByUserEmail(String email);
    
}
