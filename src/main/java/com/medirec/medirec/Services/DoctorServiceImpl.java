package com.medirec.medirec.Services;

import com.medirec.medirec.Dto.DoctorCompleteRegistrationDto;
import com.medirec.medirec.Dto.Response;
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

    public void registerDoctor(Doctor doctor) throws IllegalStateException{

        boolean emailExists = doctorRepository.findByUserEmail(doctor.getUserEmail()).isPresent();
        boolean docExists = doctorRepository.findByUserDoc(doctor.getUserDoc()).isPresent();
        boolean professionalCardExists = doctorRepository.findByDoctorProfessionalCard(doctor.getDoctorProfessionalCard()).isPresent();

        if(emailExists){
            throw new IllegalStateException("Email already taken");
        }else if(docExists){
            throw new IllegalStateException("Doctor with such document is already registered");
        }else if(professionalCardExists){
            throw new IllegalStateException("Doctor with such professional card is already registered");
        }else{
            String encodedPassword = encoder.encode(doctor.getUserPassword());
            doctor.setUserPassword(encodedPassword);
            
            doctorRepository.save(doctor);
            
            Doctor addedDoctor = doctorRepository.findByUserEmail(doctor.getUserEmail()).get();
            
            Role role = roleRepository.findByRoleName("DOCTOR").get();
            
            doctorRepository.addRole(addedDoctor.getUserId(), role.getRoleId());

        }

    }

    public void completeRegistration(DoctorCompleteRegistrationDto doctorDto) throws IllegalStateException{

        Optional<Doctor> result = doctorRepository.findById(doctorDto.getId());

        if (!result.isPresent()) {
            throw new IllegalStateException("No doctor with such id");
        } else {
            Doctor doctor = result.get();
            
            Date birthDayDate;
            try {
                birthDayDate = new SimpleDateFormat("dd/MM/yyyy").parse(doctorDto.getBirthDay());
            } catch (ParseException e) {
                throw new IllegalStateException("Error parsing birthday date");
            }
            
            doctor.setUserAddress(doctorDto.getAddress());
            doctor.setUserBirthDay(birthDayDate);
            doctor.setUserGender(doctorDto.getGender());
            doctor.setDoctorConsultory(doctorDto.getConsultory());
            doctor.setDoctorExperience(doctorDto.getExperience());
            doctor.setDoctorUniversity(doctorDto.getUniversity());
            
            doctorRepository.save(doctor);

        }
    }

    public ResponseEntity<Response> searchByName(String name){

        name = "%" + name + "%";

        List<Doctor> results = doctorRepository.searchByFirstNameAndLastName(name);
        
        String message = results.isEmpty() ? "No doctors found" : "Search done correctly";
        
        Response response = new Response(
            HttpStatus.OK.toString(),
            message,
            results
        );

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    public ResponseEntity<Response> searchBySpecialization(String specialization){

        specialization = "%" + specialization + "%";

        List<Doctor> results = doctorRepository.searchBySpecialization(specialization);
        
        String message = results.isEmpty() ? "No doctors found" : "Search done correctly";
        
        Response response = new Response(
            HttpStatus.OK.toString(),
            message,
            results
        );

        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

}
