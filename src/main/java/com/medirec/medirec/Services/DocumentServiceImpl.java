package com.medirec.medirec.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.medirec.medirec.Dto.Response;
import com.medirec.medirec.Models.Document;
import com.medirec.medirec.Models.MedicalHistory;
import com.medirec.medirec.Repositories.DocumentRepository;
import com.medirec.medirec.Repositories.MedicalHistoryRepository;
import com.medirec.medirec.Services.Interfaces.DocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService{
    
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public void saveDocument(MultipartFile[] files, int medicalHistoryId) throws IOException{
        MedicalHistory medicalHistory = medicalHistoryRepository.findById(medicalHistoryId).get();
        
            for (MultipartFile file : files) {    
                String documentName = file.getOriginalFilename();
                            
                Document document = new Document(documentName, file.getContentType(), file.getBytes());
    
                document.setMedicalHistory(medicalHistory);
                
                documentRepository.save(document);
            }

    }
    
    public void deleteDocuments(ArrayList<Integer> documentIds){
        
        documentRepository.deleteAllById(documentIds);

    }

    public List<Document> getDocuments(int medicalHistoryId){

        MedicalHistory medicalHistory = medicalHistoryRepository.findById(medicalHistoryId).get();
            
        return medicalHistory.getDocuments();
    }

}
