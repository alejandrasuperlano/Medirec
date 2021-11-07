package com.medirec.medirec.Services;

import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

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

    public void registerDoctor(Doctor doctor){

        boolean emailExists = doctorRepository.findByUserEmail(doctor.getUserEmail()).isPresent();
        if(emailExists){
            throw new IllegalStateException("email already taken");
        }else{
            String encodedPassword = encoder.encode(doctor.getUserPassword());
            doctor.setUserPassword(encodedPassword);
            doctorRepository.save(doctor);
        }

    }

    public void completeRegistration(int id, String address, String birthDay, String gender,
                                     String consultory, int experience, String university) {

        Optional<Doctor> result = doctorRepository.findById(id);

        if (!result.isPresent()) {
            throw new IllegalStateException("no doctor with given id");
        } else {
            Doctor doctor = result.get();

            Date birthDayDate;
            try {
                birthDayDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2001");
            } catch (ParseException e) {
                throw new IllegalStateException("error parsing birthday date");
            }

            doctor.setUserAddress(address);
            doctor.setUserBirthDay(birthDayDate);
            doctor.setUserGender(gender);
            doctor.setDoctorConsultory(consultory);
            doctor.setDoctorExperience(experience);
            doctor.setDoctorUniversity(university);

            doctorRepository.save(doctor);
        }
    }
}
