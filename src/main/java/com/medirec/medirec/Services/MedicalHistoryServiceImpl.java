package com.medirec.medirec.Services;

import java.util.Optional;

import com.medirec.medirec.Models.MedicalHistory;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Repositories.MedicalHistoryRepository;
import com.medirec.medirec.Services.Interfaces.MedicalHistoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalHistoryServiceImpl implements MedicalHistoryService{
    
    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    @Autowired
    private PatientServiceImpl patientRepository;

    public Optional<MedicalHistory> getMedicalHistoryById(int id){
        return medicalHistoryRepository.findById(id);
    }

    public void saveMedicalHistory(MedicalHistory medicalHistory){
        medicalHistoryRepository.save(medicalHistory);
    }

    public MedicalHistory getMedicalHistory(int patientId) throws IllegalStateException{
        Patient patient = patientRepository.getPatientById(patientId);
        if(patient == null){
            throw new IllegalStateException("No patient with such id");
        }
        
        return patient.getPatientMedicalHistory();
    }
}
