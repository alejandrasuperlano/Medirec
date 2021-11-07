package com.medirec.medirec.Repositories;

import java.util.Optional;

import com.medirec.medirec.Models.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    public Optional<Patient> findByUserEmail(String email);
}
