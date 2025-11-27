package com.example.Pokemon.repository;

import com.example.Pokemon.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String UserName);
    Boolean existsByUsername(String Username);
}
