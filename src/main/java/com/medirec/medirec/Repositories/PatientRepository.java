package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Patient;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface PatientRepository extends CrudRepository<Patient, Integer> {

    Optional<Patient> findPatientByUserEmail (String email);
    Optional<Patient> findByUserEmail(String email);
    Optional<Patient> findByUserDoc(String doc);
    
    @Modifying
    @Query(
        value = "INSERT INTO user_roles VALUES (:userId,:roleId,NULL);",
        nativeQuery = true
    )
    public void addRole(@Param("userId") int userId, @Param("roleId") long roleId);

    Patient getPatientByPasswordResetTokenPatient(String token);
}
