package com.medirec.medirec.Services;

import com.medirec.medirec.Repositories.AccessRepository;
import com.medirec.medirec.Services.Interfaces.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    @Autowired
    AccessRepository accessRepository;
}
