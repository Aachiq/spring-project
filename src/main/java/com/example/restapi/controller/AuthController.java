package com.example.restapi.controller;

import com.example.restapi.dto.SignupRequestDTO;
import com.example.restapi.dto.SignupResponseDTO;
import com.example.restapi.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AuthController {

    private final UserAuthService userAuthService;

    public AuthController(UserAuthService authService){
        this.userAuthService = authService;
    }

    @PostMapping("/signup")
    public SignupResponseDTO signupUser(@RequestBody @Valid SignupRequestDTO userSignup){
        return userAuthService.registerUser(userSignup);
    }
}
