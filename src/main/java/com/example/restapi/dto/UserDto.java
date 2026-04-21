package com.example.restapi.dto;

public class UserDto {
    private String name;
    private int phone;
    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPhone(){
        return this.phone;
    }

    public void setPhone(int phone){
        this.phone = phone;
    }
}
