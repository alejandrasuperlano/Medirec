package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{
    
}
