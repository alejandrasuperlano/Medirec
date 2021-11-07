package com.medirec.medirec.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Services.Interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository repository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public void registerPatient(Patient patient){

        boolean emailExists = repository.findByUserEmail(patient.getUserEmail()).isPresent();
        if(emailExists){
            throw new IllegalStateException("email already taken");
        }else{
            String encodedPassword = encoder.encode(patient.getUserPassword());
            patient.setUserPassword(encodedPassword);
            repository.save(patient);
        }
        
    }
    
    public void completeRegistration(int id, String address, String birthDay, String gender,String maritalStatus){
        
        Optional<Patient> result = repository.findById(id);
        
        if(!result.isPresent()){
            throw new IllegalStateException("no patient with given id");
        }else{
            Patient patient = result.get();
            
            Date birthDayDate;
            try{
                birthDayDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/2001");  
            }catch (ParseException e){
                throw new IllegalStateException("error parsing birthday date");
            }

            patient.setUserAddress(address);
            patient.setUserBirthDay(birthDayDate);
            patient.setUserGender(gender);
            patient.setPatientMaritalStatus(maritalStatus);
            
            repository.save(patient);
        }
    }
}
