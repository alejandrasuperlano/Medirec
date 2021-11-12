package com.medirec.medirec.Services;

import com.medirec.medirec.Dto.DoctorCompleteRegistrationDto;
import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Doctor;
import com.medirec.medirec.Models.Role;
import com.medirec.medirec.Repositories.DoctorRepository;
import com.medirec.medirec.Repositories.PasswordTokenDoctorRepository;
import com.medirec.medirec.Repositories.PasswordTokenPatientRepository;
import com.medirec.medirec.Repositories.RoleRepository;
import com.medirec.medirec.Security.Model.PasswordResetTokenDoctor;
import com.medirec.medirec.Services.Interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    PasswordTokenPatientRepository passwordTokenPatientRepository;

    @Autowired
    PasswordTokenDoctorRepository passwordTokenRepoDoctor;

    @Override
    public Doctor getDoctorByEmail(String email) {
        if (doctorRepository.findDoctorByUserEmail(email).isPresent()) {
            return doctorRepository.findDoctorByUserEmail(email).get();
        } else {
            return null;
        }
    }

    @Override
    public boolean passwordConfirm(String password, String passwordConfirmed) {
        return passwordConfirmed.equals(password);
    }

    @Override
    public void passwordRecovery(Doctor doctorDB, String newPassword) {
        String encodedPassword = encoder.encode(newPassword);
        doctorDB.setUserPassword(encodedPassword);
        doctorRepository.save(doctorDB);
    }

    public ResponseEntity<String> registerDoctor(Doctor doctor){

        boolean emailExists = doctorRepository.findByUserEmail(doctor.getUserEmail()).isPresent();
        boolean docExists = doctorRepository.findByUserDoc(doctor.getUserDoc()).isPresent();
        boolean professionalCardExists = doctorRepository.findByDoctorProfessionalCard(doctor.getDoctorProfessionalCard()).isPresent();

        if(emailExists){
            return new ResponseEntity<String>("Email already taken", HttpStatus.BAD_REQUEST);
        }else if(docExists){
            return new ResponseEntity<String>("Doctor with such document is already registered", HttpStatus.BAD_REQUEST);
        }else if(professionalCardExists){
            return new ResponseEntity<String>("Doctor with such professional card is already registered", HttpStatus.BAD_REQUEST);
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
    @Override
    public void createPasswordResetTokenForUser(Doctor doctor, String token) {
        PasswordResetTokenDoctor myToken = new PasswordResetTokenDoctor(token, doctor);
        passwordTokenRepoDoctor.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetTokenDoctor passToken = passwordTokenRepoDoctor.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetTokenDoctor passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetTokenDoctor passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

}
