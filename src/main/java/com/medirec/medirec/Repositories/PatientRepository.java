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

    @Query(
            value = "select pt.user_id, \n" +
                    "       pt.user_address, \n" +
                    "       pt.user_birth_day, \n" +
                    "       pt.user_doc_type, \n" +
                    "       pt.user_email, \n" +
                    "       pt.user_first_name, \n" +
                    "       pt.user_gender, \n" +
                    "       pt.user_last_name, \n" +
                    "       pt.user_password, \n" +
                    "       pt.user_tutorial, \n" +
                    "       pt.patient_eps, \n" +
                    "       pt.patient_marital_status, \n" +
                    "       pt.medical_history_id, \n" +
                    "       pt.user_doc\n" +
                    "from patient as pt inner join password_reset_token_patient as reset on pt.user_id = reset.patient_recover_id \n" +
                    "where reset.patient_recover_id = :patientId and \n" +
                    "reset.token = :token",
            nativeQuery = true
    )
    Patient findPatientByTokenAndId(@Param("patientId") int patientId,
                                    @Param("token") String token);
}
