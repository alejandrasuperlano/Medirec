package com.medirec.medirec.Repositories;

import com.medirec.medirec.Security.Model.PasswordResetTokenDoctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenDoctorRepository extends CrudRepository<PasswordResetTokenDoctor, Long> {

    PasswordResetTokenDoctor findByToken(String token);
}
