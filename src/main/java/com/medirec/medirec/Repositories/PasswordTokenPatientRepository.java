package com.medirec.medirec.Repositories;

import com.medirec.medirec.Security.Model.PasswordResetTokenPatient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenPatientRepository extends CrudRepository<PasswordResetTokenPatient, Long> {

    PasswordResetTokenPatient findByToken(String token);
}
