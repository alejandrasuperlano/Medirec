package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Score;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ScoreRepository extends CrudRepository<Score, Integer>{
    
}
