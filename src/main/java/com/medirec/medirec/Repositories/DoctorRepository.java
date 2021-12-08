package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Doctor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface DoctorRepository extends CrudRepository <Doctor, Integer>{

    Optional<Doctor> findDoctorByUserEmail (String email);
    Optional<Doctor> findByUserEmail(String email);
    Optional<Doctor> findByUserDoc(String doc);
    Optional<Doctor> findByDoctorProfessionalCard(String card);

    @Query(
            value = "select dr.user_id, \n" +
                    "       dr.user_address, \n" +
                    "       dr.user_birth_day, \n" +
                    "       dr.user_doc_type, \n" +
                    "       dr.user_email, \n" +
                    "       dr.user_first_name, \n" +
                    "       dr.user_gender, \n" +
                    "       dr.user_last_name, \n" +
                    "       dr.user_password, \n" +
                    "       dr.user_tutorial, \n" +
                    "       dr.doctor_consultory, \n" +
                    "       dr.doctor_experience, \n" +
                    "       dr.doctor_experience, \n" +
                    "       dr.doctor_professional_card, \n" +
                    "       dr.doctor_specialization, \n" +
                    "       dr.doctor_university, \n" +
                    "       dr.user_doc\n" +
                    "from doctor as dr inner join password_reset_token_doctor as reset on dr.user_id = reset.doctor_recover_id \n" +
                    "where reset.doctor_recover_id = :doctorId and \n" +
                    "reset.token = :token",
            nativeQuery = true
    )
    Doctor findDoctorByTokenAndId(@Param("doctorId") int doctorId,
                                  @Param("token") String token);
    
    @Query(
        value = "SELECT * FROM doctor WHERE doctor.user_first_name LIKE ?1 OR doctor.user_last_name LIKE ?1",
        nativeQuery = true
    )
    List<Doctor> searchByFirstNameAndLastName(String name);

    @Query(
        value = "SELECT * FROM doctor WHERE doctor.doctor_specialization LIKE ?1",
        nativeQuery = true
    )
    List<Doctor> searchBySpecialization(String specialization);

    @Modifying
    @Query(
        value = "INSERT INTO user_roles VALUES (:userId,:roleId,NULL);",
        nativeQuery = true
    )
    public void addRole(@Param("userId") int userId, @Param("roleId") long roleId);
    
}
