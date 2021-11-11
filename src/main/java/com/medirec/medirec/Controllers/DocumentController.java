package com.medirec.medirec.Controllers;

import java.io.IOException;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Patient;
import com.medirec.medirec.Services.DocumentServiceImpl;
import com.medirec.medirec.Services.MedicalHistoryServiceImpl;
import com.medirec.medirec.Services.PatientServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/patient/documents")
public class DocumentController {
    
    @Autowired 
    DocumentServiceImpl documentService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    MedicalHistoryServiceImpl medicalHistoryService;

    @PostMapping(path = "{patientId}")
    public ResponseEntity<String> uploadDocuments(
        @PathVariable("patientId") int patientId,
        @RequestParam("files") MultipartFile[] files
    ){  
        Patient patient;

        try {
            patient = patientService.getPatientById(patientId);        
        } catch (IllegalStateException e) {
            return new ResponseEntity<String>("No patient with such id", HttpStatus.BAD_REQUEST);   
        }
        
        int medicalHistoryId = patient.getPatientMedicalHistory().getMedicalHistoryId();
        
        try {
            documentService.saveDocument(files, medicalHistoryId);
        } catch (IOException e) {
            return new ResponseEntity<String>("Error saving files", HttpStatus.INTERNAL_SERVER_ERROR);   
        }
        
        return new ResponseEntity<String>("Documents saved succesfully", HttpStatus.OK);   
    }
    
    @GetMapping(path = "{patientId}")
    public ResponseEntity<Response> getDocuments(
        @PathVariable("patientId") int patientId
        ){
            Patient patient;
            Response response;

            try {
                patient = patientService.getPatientById(patientId);
            } catch (Exception e) {
                response = new Response(HttpStatus.BAD_REQUEST.toString(), "No patient with such id", null);
                return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
        }

        int medicalHistoryId = patient.getPatientMedicalHistory().getMedicalHistoryId();

        return documentService.getDocuments(medicalHistoryId);
    }

}
