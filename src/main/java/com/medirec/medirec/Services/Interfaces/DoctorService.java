package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Models.Doctor;

public interface DoctorService {

    Doctor getDoctorByEmail (String email);
}
