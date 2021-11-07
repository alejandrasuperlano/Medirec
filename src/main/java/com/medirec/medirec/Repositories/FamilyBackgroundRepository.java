package com.medirec.medirec.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FamilyBackgroundRepository extends CrudRepository<FamilyBackgroundRepository, Integer>{
    
}
