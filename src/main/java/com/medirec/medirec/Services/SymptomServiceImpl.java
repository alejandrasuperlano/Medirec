package com.medirec.medirec.Services;

import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Models.Symptom;
import com.medirec.medirec.Repositories.PatientRepository;
import com.medirec.medirec.Repositories.SymptomRepository;
import com.medirec.medirec.Services.Interfaces.SymptomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SymptomServiceImpl implements SymptomService{
    
    @Autowired
    SymptomRepository symptomRepository;

    @Autowired
    PatientRepository patientRepository;


    @Override
    public Symptom saveSymptom(Symptom symptom, int patientId) throws Exception {
        Patient patient = patientRepository.findById(patientId).get();
        symptom.setMedicalHistory(patient.getPatientMedicalHistory());
        return symptomRepository.save(symptom);
    }
}
