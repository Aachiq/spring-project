package com.example.restapi.dto;

public class SignupResponseDTO {

    private String message;

    public SignupResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
