package com.example.restapi.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String sayHi(String name){
        return "Hi " + name;
    }

    public String getUserById(int id){
        return "The Params ID is : "+ id;
    }

    public String deleteById(int id){
        return "The Params ID to delete is : "+ id;
    }

}
