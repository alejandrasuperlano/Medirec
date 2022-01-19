package com.medirec.medirec.config;

import java.util.ArrayList;
import java.util.List;

import com.medirec.medirec.Models.Role;
import com.medirec.medirec.Repositories.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {
    
    @Bean
    CommandLineRunner addRoles(RoleRepository repository){
        return args ->{
            Role patientRole = new Role("PATIENT");
            Role doctorRole = new Role("DOCTOR");
            ArrayList<Role> roleArrayList = new ArrayList<>();
            roleArrayList.add(patientRole);
            roleArrayList.add(doctorRole);
            repository.saveAll(roleArrayList);
        };
    }
}
