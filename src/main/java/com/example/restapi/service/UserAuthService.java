package com.example.restapi.service;

import com.example.restapi.dto.SignupRequestDTO;
import com.example.restapi.model.UserAuth;
import com.example.restapi.repository.UserAuthRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserAuthService {

    // inject Repository
    private final UserAuthRepository userAuthRepository;

    public UserAuthService(UserAuthRepository userAuthRepo){
        this.userAuthRepository = userAuthRepo;
    }

    public String registerUser(SignupRequestDTO userData){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserAuth user = new UserAuth(userData.getName(), userData.getEmail(), encoder.encode(userData.getPassword()));
        userAuthRepository.save(user);
        return "User Registred Successfully";
    }
}
