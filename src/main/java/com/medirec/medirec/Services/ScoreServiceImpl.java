package com.medirec.medirec.Services;

import java.util.Optional;

import com.medirec.medirec.Models.Score;
import com.medirec.medirec.Repositories.ScoreRepository;
import com.medirec.medirec.Services.Interfaces.ScoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService{
    
    @Autowired
    private ScoreRepository repository;

    public void saveScore(Score score){
        repository.save(score);
    }

    public Score getScoreById(int id){
        Optional<Score> score = repository.findById(id);
        
        if(score.isPresent()){
            return score.get();
        }else{
            return null;
        }
    }
}
