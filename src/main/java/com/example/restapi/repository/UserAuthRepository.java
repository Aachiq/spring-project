package com.example.restapi.repository;

import com.example.restapi.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {
    boolean existsByEmail(String email);
    Optional<UserAuth> findUserAuthByEmail(String email);
}
