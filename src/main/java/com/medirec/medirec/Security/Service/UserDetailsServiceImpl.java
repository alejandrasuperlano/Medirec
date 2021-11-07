package com.medirec.medirec.Security.Service;

import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.User;
import com.medirec.medirec.Security.Model.UserSessionDetails;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import com.medirec.medirec.Services.Interfaces.PatientService;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Autowired
    DoctorService doctorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (patientService.getPatientByEmail(username) != null){
            Patient patient = patientService.getPatientByEmail(username);
            return UserSessionDetails.build(patient);
        } else if (doctorService.getDoctorByEmail(username) != null){
            Doctor doctor = doctorService.getDoctorByEmail(username);
            return UserSessionDetails.build(doctor);
        }
        return null;
    }
}
