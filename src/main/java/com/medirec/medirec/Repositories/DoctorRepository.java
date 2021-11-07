package com.medirec.medirec.Repositories;

import java.util.Optional;

import com.medirec.medirec.Models.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DoctorRepository extends CrudRepository <Doctor, Integer>{
    
    public Optional<Doctor> findByUserEmail(String email);
    
    
}
