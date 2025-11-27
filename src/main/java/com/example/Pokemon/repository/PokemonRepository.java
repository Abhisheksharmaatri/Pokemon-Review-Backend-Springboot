package com.example.Pokemon.repository;

import com.example.Pokemon.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {
    Optional<Pokemon> findByName(String name);
    Optional<Pokemon> findByType(String type);
}
