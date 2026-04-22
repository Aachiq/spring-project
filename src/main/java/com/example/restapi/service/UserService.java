package com.example.restapi.service;

import com.example.restapi.model.User;
import com.example.restapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String sayHi(String name){
        return "Hi " + name;
    }

    public String getUserById(int id){
        return "The Params ID is : "+ id;
    }

    public String deleteById(int id){
        return "The Params ID to delete is : "+ id;
    }

    // service get from db using repository
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }
}
