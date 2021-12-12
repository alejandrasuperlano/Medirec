package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Access;
import com.medirec.medirec.Models.AccessPK;
import com.medirec.medirec.Models.Doctor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface AccessRepository extends CrudRepository<Access, AccessPK> {

    @Query(value =  "select * " +
                    "from access " +
                    "where access.permission = 0 and access.patient = :idPatient", nativeQuery = true)
    List<Access> getRequestsByPatientUserId (@Param("idPatient") int idPatient);

    @Query(value =  "select * " +
                    "from access " +
                    "where access.permission = 1 and access.patient = :idPatient", nativeQuery = true)
    List<Access> getMyDoctorsByPatientId (@Param("idPatient") int idPatient);

    @Query(value =  "select * " +
            "from access " +
            "where access.permission = 1 and access.doctor = :idDoctor", nativeQuery = true)
    List<Access> getMyPatientsByDoctorId (@Param("idDoctor") int idDoctor);

    @Query(value =  "select * " +
                    "from access " +
                    "where access.patient = :patientId and access.doctor = :doctorId " +
                            "and access.permission = 0", nativeQuery = true)
    Optional<Access> getAccessByPatientIdAndDoctorId(@Param("doctorId") int doctorId,
                                                     @Param("patientId") int patientId);


}
