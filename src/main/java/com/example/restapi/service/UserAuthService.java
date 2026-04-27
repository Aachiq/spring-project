package com.example.restapi.service;

import com.example.restapi.dto.SigninRequestDTO;
import com.example.restapi.dto.SignupRequestDTO;
import com.example.restapi.dto.SignupResponseDTO;
import com.example.restapi.model.User;
import com.example.restapi.model.UserAuth;
import com.example.restapi.repository.UserAuthRepository;
import com.example.restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserAuthService {

    // inject Repository
    private final UserAuthRepository userAuthRepository;
    private final UserRepository userRepository;

    public UserAuthService(UserAuthRepository userAuthRepo, UserRepository userRepository){
        this.userAuthRepository = userAuthRepo;
        this.userRepository = userRepository;
    }

    public SignupResponseDTO registerUser(SignupRequestDTO userData){

        if(userAuthRepository.existsByEmail(userData.getEmail())){
            return new SignupResponseDTO("User Already Exist");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        UserAuth user = new UserAuth(userData.getName(), userData.getEmail(), encoder.encode(userData.getPassword()));
        userAuthRepository.save(user);
        return new SignupResponseDTO("User Registred Successfully");
    }

    public String login(SigninRequestDTO loginData){

        System.out.println("LoginData : "+ loginData.getEmail());
        Optional<UserAuth> foundUser = userAuthRepository.findUserAuthByEmail(loginData.getEmail());
        System.out.println("foundUser : "+ foundUser.toString());

        // use isEmpty() to check if no data
        if(foundUser.isEmpty()){
            return "User Email not Found !";
        }
        // use get() if email exist
        UserAuth user = foundUser.get();

        // check password comparison
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(loginData.getPassword(), user.getPassword())){
            return "Password not matched !";
        }
        return "Login successful";
    }
}
