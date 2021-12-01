package com.medirec.medirec.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import com.medirec.medirec.Dto.PatientCompleteRegistrationDto;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.Role;
import com.medirec.medirec.Repositories.PasswordTokenPatientRepository;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Repositories.RoleRepository;
import com.medirec.medirec.Security.Model.PasswordResetTokenPatient;
import com.medirec.medirec.Services.Interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    PasswordTokenPatientRepository passwordTokenPatientRepository;

    public void registerPatient(Patient patient) throws IllegalStateException{

        boolean emailExists = patientRepository.findByUserEmail(patient.getUserEmail()).isPresent();
        boolean docExists = patientRepository.findByUserDoc(patient.getUserDoc()).isPresent();

        if(emailExists){
            throw new IllegalStateException("Email already taken");
        }else if(docExists){
            throw new IllegalStateException("Patient with such document is already registered");    
        }else{
            String encodedPassword = encoder.encode(patient.getUserPassword());
            patient.setUserPassword(encodedPassword);

            patientRepository.save(patient);

            Patient addedPatient = patientRepository.findByUserEmail(patient.getUserEmail()).get();
            
            Role role = roleRepository.findByRoleName("PATIENT").get();
            
            patientRepository.addRole(addedPatient.getUserId(), role.getRoleId());

        }
        
    }
    
    public void completeRegistration(PatientCompleteRegistrationDto patientDto){
        
        Optional<Patient> result = patientRepository.findById(patientDto.getId());
        
        if(!result.isPresent()){
            throw new IllegalStateException("No patient with such id");
        }else{
            Patient patient = result.get();
            
            Date birthDayDate;
            try{
                birthDayDate = new SimpleDateFormat("dd/MM/yyyy").parse(patientDto.getBirthDay());  
            }catch (ParseException e){
                throw new IllegalStateException("Error parsing birthday date");
            }
            
            patient.setUserAddress(patientDto.getAddress());
            patient.setUserBirthDay(birthDayDate);
            patient.setUserGender(patientDto.getGender());
            patient.setPatientMaritalStatus(patientDto.getMaritalStatus());
            
            patientRepository.save(patient);

            
        }
    }

    @Override
    public Patient getPatientByEmail(String email){
        if(patientRepository.findPatientByUserEmail(email).isPresent()){
            return patientRepository.findPatientByUserEmail(email).get();
        } else {
            return null;
        }
    }

    public Patient getPatientById(int id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isPresent()){
            return patient.get();
        } else {
            return null;
        }
    }

    public void savePatient(Patient patient){
        patientRepository.save(patient);
    }

    @Override
    public boolean passwordConfirm(String password, String confirmPass) {
        return confirmPass.equals(password);
    }

    @Override
    public void passwordRecovery(Patient patientDB, String newPassword) {
        String encodedPassword = encoder.encode(newPassword);
        patientDB.setUserPassword(encodedPassword);
        patientRepository.save(patientDB);
    }

    @Override
    public void createPasswordResetTokenForUser(Patient patient, String token) {
        PasswordResetTokenPatient myToken = new PasswordResetTokenPatient(token, patient);
        passwordTokenPatientRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetTokenPatient passToken = passwordTokenPatientRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetTokenPatient passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetTokenPatient passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

}
