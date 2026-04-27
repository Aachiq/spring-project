package com.example.restapi.dto;

public class SigninRequestDTO {
    private String email;
    private String password;

    // getters & setters
    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    // getters & setters
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String pass){
        this.password = pass;
    }
}
