package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Illness;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IllnessRepository extends CrudRepository<Illness, Integer>{
    
}
