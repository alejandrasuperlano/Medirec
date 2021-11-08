package com.medirec.medirec.Repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import com.medirec.medirec.Models.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

    Optional<Role> findByRoleName(String name);
    
}
