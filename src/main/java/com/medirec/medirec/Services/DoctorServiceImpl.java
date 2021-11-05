package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
}
