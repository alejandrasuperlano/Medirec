package com.medirec.medirec.Controllers;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.MedicalHistory;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Services.DocumentServiceImpl;
import com.medirec.medirec.Services.MedicalHistoryServiceImpl;
import com.medirec.medirec.Services.PatientServiceImpl;
import com.medirec.medirec.Services.Interfaces.DocumentService;
import com.medirec.medirec.Services.Interfaces.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/patientDocuments")
public class DocumentController {
    
    @Autowired 
    DocumentServiceImpl documentService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    MedicalHistoryServiceImpl medicalHistoryService;

    @PostMapping
    public ResponseEntity<String> uploadDocuments(
        @RequestParam("patientId") int patientId,
        @RequestParam("files") MultipartFile[] files
    ){
        Patient patient = patientService.getPatientById(patientId);
        if(patient == null){
            return new ResponseEntity<String>("No patient with such id", HttpStatus.BAD_REQUEST);
        }

        int medicalHistoryId = patient.getPatientMedicalHistory().getMedicalHistoryId();

        return documentService.saveDocument(files, medicalHistoryId);
    }

    @GetMapping
    public ResponseEntity<Response> getDocuments(
        @RequestParam("patientId") int patientId
    ){
        Patient patient = patientService.getPatientById(patientId);
        if(patient == null){
            Response response = new Response(HttpStatus.BAD_REQUEST.toString(), "No patient with such id", null);

            return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        int medicalHistoryId = patient.getPatientMedicalHistory().getMedicalHistoryId();

        return documentService.getDocuments(medicalHistoryId);
    }

}
