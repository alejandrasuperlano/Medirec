package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Score;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Integer>{
    
}
