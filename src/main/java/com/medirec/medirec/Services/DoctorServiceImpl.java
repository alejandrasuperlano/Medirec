package com.medirec.medirec.Services;

import com.medirec.medirec.Dto.DoctorCompleteRegistrationDto;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Role;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Repositories.RoleRepository;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public Doctor getDoctorByEmail(String email) {
        if (doctorRepository.findDoctorByUserEmail(email).isPresent()) {
            return doctorRepository.findDoctorByUserEmail(email).get();
        } else {
            return null;
        }
    }

    public ResponseEntity<String> registerDoctor(Doctor doctor){

        boolean emailExists = doctorRepository.findByUserEmail(doctor.getUserEmail()).isPresent();
        if(emailExists){
            return new ResponseEntity<String>("Email already taken", HttpStatus.BAD_REQUEST);
        }else{
            String encodedPassword = encoder.encode(doctor.getUserPassword());
            doctor.setUserPassword(encodedPassword);
            
            doctorRepository.save(doctor);
            
            Doctor addedDoctor = doctorRepository.findByUserEmail(doctor.getUserEmail()).get();
            
            Role role = roleRepository.findByRoleName("DOCTOR").get();
            
            doctorRepository.addRole(addedDoctor.getUserId(), role.getRoleId());

            return new ResponseEntity<String>("Doctor registered succesfully", HttpStatus.OK);
        }

    }

    public ResponseEntity<String> completeRegistration(DoctorCompleteRegistrationDto doctorDto) {

        Optional<Doctor> result = doctorRepository.findById(doctorDto.getId());

        if (!result.isPresent()) {
            return new ResponseEntity<String>("No doctor with given id", HttpStatus.BAD_REQUEST);
        } else {
            Doctor doctor = result.get();
            
            Date birthDayDate;
            try {
                birthDayDate = new SimpleDateFormat("dd/MM/yyyy").parse(doctorDto.getBirthDay());
            } catch (ParseException e) {
                return new ResponseEntity<String>("Error parsing birthday date", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            doctor.setUserAddress(doctorDto.getAddress());
            doctor.setUserBirthDay(birthDayDate);
            doctor.setUserGender(doctorDto.getGender());
            doctor.setDoctorConsultory(doctorDto.getConsultory());
            doctor.setDoctorExperience(doctorDto.getExperience());
            doctor.setDoctorUniversity(doctorDto.getUniversity());
            
            doctorRepository.save(doctor);

            return new ResponseEntity<String>("Doctor complete registration succesfully", HttpStatus.OK);
        }
    }
}
