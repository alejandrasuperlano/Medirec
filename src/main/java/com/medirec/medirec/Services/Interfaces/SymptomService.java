package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Models.Symptom;

public interface SymptomService {

    Symptom saveSymptom (Symptom symptom, int patientId) throws Exception;
    
}
