package com.medirec.medirec.Services;

import java.util.List;

import com.medirec.medirec.Models.PersonalRecord;
import com.medirec.medirec.Repositories.PersonalRecordRepository;
import com.medirec.medirec.Services.Interfaces.PersonalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalRecordServiceImpl implements PersonalRecordService{
    
    @Autowired
    PersonalRecordRepository repository;

    public void savePersonalRecord(PersonalRecord record){
        repository.save(record);
    }

    public void savePersonalRecords(List<PersonalRecord> records){
        repository.saveAll(records);
    }
}
