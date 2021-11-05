package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.MedicalHistory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MedicalHistoryRepository extends CrudRepository<MedicalHistory, Integer>{
    
}
