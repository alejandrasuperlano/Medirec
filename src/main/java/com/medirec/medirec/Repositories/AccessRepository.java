package com.medirec.medirec.Repositories;

import com.medirec.medirec.Models.Access;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccessRepository extends CrudRepository<Access, Integer> {
}
