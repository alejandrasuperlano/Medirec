package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Doctor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface DoctorRepository extends CrudRepository <Doctor, Integer>{

    Optional<Doctor> findDoctorByUserEmail (String email);
    Optional<Doctor> findByUserEmail(String email);

    @Modifying
    @Query(
        value = "INSERT INTO user_roles VALUES (NULL,:roleId,:userId);",
        nativeQuery = true
    )
    public void addRole(@Param("userId") int userId, @Param("roleId") long roleId);
    
}
