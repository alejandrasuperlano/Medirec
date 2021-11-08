package com.medirec.medirec.Repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import com.medirec.medirec.Models.Role;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

    Optional<Role> findByRoleName(String name);

    @Modifying
    @Query(
        nativeQuery = true,
        value = "ALTER TABLE medirec.user_roles DROP FOREIGN KEY FK4m7ds6r5ybh3dnedhh8vjvx5s, DROP FOREIGN KEY FK7vm8gyi7dj1ovkv2ncp9t7x4e; ALTER TABLE medirec.user_roles CHANGE patient_id patient_id INT NULL , CHANGE doctor_id doctor_id INT NULL ; ALTER TABLE medirec.user_roles ADD CONSTRAINT FK4m7ds6r5ybh3dnedhh8vjvx5s FOREIGN KEY (doctor_id) REFERENCES medirec.doctor (user_id), ADD CONSTRAINT FK7vm8gyi7dj1ovkv2ncp9t7x4e FOREIGN KEY (patient_id) REFERENCES medirec.patient (user_id);"
    )
    public void modifyNullableColumns();
    
}
