package com.example.restapi.dto;

public class SigninResponseDTO {

        private String name;
        private String email;
        private String message;
        // private String token;
        //private String role;

        public SigninResponseDTO(String name, String email, String message) {
            this.name = name;
            this.email = email;
            this.message = message;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getMessage() { return message; }
}
