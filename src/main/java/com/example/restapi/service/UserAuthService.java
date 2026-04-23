package com.example.restapi.service;

import com.example.restapi.dto.SignupRequestDTO;
import com.example.restapi.model.UserAuth;
import com.example.restapi.repository.UserAuthRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

    // inject Repository
    private final UserAuthRepository userAuthRepository;

    public UserAuthService(UserAuthRepository userAuthRepo){
        this.userAuthRepository = userAuthRepo;
    }

    public String registerUser(SignupRequestDTO userData){
        UserAuth user = new UserAuth(userData.getName(), userData.getEmail(), userData.getPassword());
        userAuthRepository.save(user);
        return "User Registred Successfully";
    }
}
