package com.medirec.medirec.Repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import com.medirec.medirec.Models.Appointment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository repository;

    @Test
    public void addAppointment(){
        Appointment appointment = new Appointment("Control", LocalDate.now(), "Revisión de tensión");

        repository.save(appointment);
    }
}