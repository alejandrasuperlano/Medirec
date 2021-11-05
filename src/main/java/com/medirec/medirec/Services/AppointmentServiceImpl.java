package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.AppointmentRepository;
import com.medirec.medirec.Services.Interfaces.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImpl implements AppointmentService{
    
    @Autowired
    private AppointmentRepository repository;
}
