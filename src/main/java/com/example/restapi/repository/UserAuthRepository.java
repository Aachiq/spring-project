package com.example.restapi.repository;

import com.example.restapi.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
}
