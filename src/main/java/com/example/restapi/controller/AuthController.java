package com.example.restapi.controller;

import com.example.restapi.dto.SigninRequestDTO;
import com.example.restapi.dto.SigninResponseDTO;
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

    @PostMapping("/signin")
    public SigninResponseDTO signin(@RequestBody SigninRequestDTO userSignin){
        return userAuthService.login(userSignin);
    }

    @PostMapping("/signout")
    public String logout() {
        // For simple apps, logout is client-side: just delete token
        return "Logged out successfully";
    }

}
