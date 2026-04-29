package com.example.restapi.model;

import jakarta.persistence.*;

@Entity
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING) // important for Enums
    private Role role;

    public UserAuth() {
    }

    // don't include id in constructore becasue in creation User we won't pass ID
    public UserAuth(String name, String email, String password, Role role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // getters & setters
    public Long getId(){ return this.id;}
    public void setId(Long id){ this.id = id;}
    public String getName(){ return this.name;}
    public void setName(String name){ this.name = name;}
    public String getEmail(){ return this.email;}
    public void setEmail(String email){ this.email = email;}
    public String getPassword(){ return this.password;}
    public void setPassword(String password){ this.password = password;}
    public Role getRole(){ return this.role;}
    public void setRole(Role role){ this.role = role;}

}
