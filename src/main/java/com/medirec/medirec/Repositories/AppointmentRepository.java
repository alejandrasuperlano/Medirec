package com.medirec.medirec.Repositories;


import com.medirec.medirec.Models.Appointment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AppointmentRepository extends CrudRepository<Appointment, Integer>{
    
}
