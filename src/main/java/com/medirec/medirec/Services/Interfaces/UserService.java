package com.medirec.medirec.Services.Interfaces;

import com.medirec.medirec.Models.User;

public interface UserService {

    User getUserByEmail(String email);
}
