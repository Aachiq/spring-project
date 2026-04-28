package com.example.restapi.service;

import com.example.restapi.dto.SigninRequestDTO;
import com.example.restapi.dto.SigninResponseDTO;
import com.example.restapi.dto.SignupRequestDTO;
import com.example.restapi.dto.SignupResponseDTO;
import com.example.restapi.model.UserAuth;
import com.example.restapi.repository.UserAuthRepository;
import com.example.restapi.security.JwtUtils;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserAuthService {

    // inject Repository
    private final UserAuthRepository userAuthRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserAuthService(UserAuthRepository userAuthRepo, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtils jwtUtils){
        this.userAuthRepository = userAuthRepo;
        this.passwordEncoder = bCryptPasswordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public SignupResponseDTO registerUser(SignupRequestDTO userData){

        if(userAuthRepository.existsByEmail(userData.getEmail())){
            return new SignupResponseDTO("User Already Exist");
        }

        UserAuth user = new UserAuth(userData.getName(), userData.getEmail(), passwordEncoder.encode(userData.getPassword()));
        userAuthRepository.save(user);
        return new SignupResponseDTO("User Registred Successfully");
    }

    public SigninResponseDTO login(SigninRequestDTO loginData){

        System.out.println("LoginData : "+ loginData.getEmail());
        Optional<UserAuth> foundUser = userAuthRepository.findUserAuthByEmail(loginData.getEmail());
        System.out.println("foundUser : "+ foundUser.toString());

        // use isEmpty() to check if no data
        if(foundUser.isEmpty()){
            return new SigninResponseDTO(null, null, "User Email not Found !", null);
        }
        // use get() if email exist
        UserAuth user = foundUser.get();

        // check password comparison
        if(!passwordEncoder.matches(loginData.getPassword(), user.getPassword())){
            return new SigninResponseDTO(null, null, "Password not matched !", null);
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return new SigninResponseDTO(user.getName(), user.getEmail(), "Login Successful !", token);
    }
}
