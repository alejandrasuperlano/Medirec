package com.medirec.medirec.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import com.medirec.medirec.Dto.PatientCompleteRegistrationDto;
import com.medirec.medirec.Dto.PatientRegistrationDto;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.Role;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Repositories.RoleRepository;
import com.medirec.medirec.Services.Interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    public ResponseEntity<String> registerPatient(Patient patient){

        boolean emailExists = patientRepository.findByUserEmail(patient.getUserEmail()).isPresent();
        if(emailExists){
            return new ResponseEntity<String>("Email already taken", HttpStatus.BAD_REQUEST);
        }else{
            String encodedPassword = encoder.encode(patient.getUserPassword());
            patient.setUserPassword(encodedPassword);

            patientRepository.save(patient);

            // Patient addedPatient = patientRepository.findByUserEmail(patient.getUserEmail()).get();
            
            // Role role = roleRepository.findByRoleName("PATIENT").get();
            
            // patientRepository.addRole(addedPatient.getUserId(), role.getRoleId());

            return new ResponseEntity<String>("Patient registered succesfully", HttpStatus.OK);
        }
        
    }
    
    public ResponseEntity<String> completeRegistration(PatientCompleteRegistrationDto patientDto){
        
        Optional<Patient> result = patientRepository.findById(patientDto.getId());
        
        if(!result.isPresent()){
            return new ResponseEntity<String>("No patient with given id", HttpStatus.BAD_REQUEST);
        }else{
            Patient patient = result.get();
            
            Date birthDayDate;
            try{
                birthDayDate = new SimpleDateFormat("dd/MM/yyyy").parse(patientDto.getBirthDay());  
            }catch (ParseException e){
                return new ResponseEntity<String>("Error parsing birthdat date", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            patient.setUserAddress(patientDto.getAddress());
            patient.setUserBirthDay(birthDayDate);
            patient.setUserGender(patientDto.getGender());
            patient.setPatientMaritalStatus(patientDto.getMaritalStatus());
            
            patientRepository.save(patient);

            return new ResponseEntity<String>("Patient complete registration succesfully", HttpStatus.OK);
        }
    }

    @Override
    public Patient getPatientByEmail(String email) {
        if(patientRepository.findPatientByUserEmail(email).isPresent()){
            return patientRepository.findPatientByUserEmail(email).get();
        } else {
            return null;
        }
    }
}
