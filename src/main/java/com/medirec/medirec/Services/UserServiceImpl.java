package com.medirec.medirec.Services;

import com.medirec.medirec.Models.User;
import com.medirec.medirec.Repositories.UserRepository;
import com.medirec.medirec.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        return null;
    }
}
