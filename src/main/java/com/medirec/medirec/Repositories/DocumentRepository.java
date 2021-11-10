package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Document;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface DocumentRepository extends CrudRepository<Document, Integer>{
    
}
