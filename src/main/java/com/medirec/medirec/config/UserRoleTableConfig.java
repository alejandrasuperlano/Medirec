package com.medirec.medirec.config;

import com.medirec.medirec.Repositories.RoleRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserRoleTableConfig {
    
    @Bean
    CommandLineRunner modifyNullableColumns(RoleRepository repository){
        return args -> {
            repository.modifyNullableColumns();
        };
    }
}
