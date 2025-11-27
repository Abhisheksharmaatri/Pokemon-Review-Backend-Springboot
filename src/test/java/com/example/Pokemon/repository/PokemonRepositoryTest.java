package com.example.Pokemon.repository;


import com.example.Pokemon.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PokemonRepositoryTest {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void PokemonRepository_SaveAll_ReturnSavedPokemon(){
//        Arrange
        Pokemon pokemon=Pokemon.builder().name("pikachu").type("electric").build();
//        Act

        Pokemon savedPokemon=pokemonRepository.save(pokemon);

//        Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);

    }

    @Test
    public void PokemonRepository_getAll_Returns_AllPokemons(){
        Pokemon pokemon1=Pokemon.builder().name("bobo").type("land").build();
        Pokemon pokemon2=Pokemon.builder().name("bobo2").type("land2").build();

        Pokemon SavedPokemon1=pokemonRepository.save(pokemon1);
        Pokemon SavedPokemon2=pokemonRepository.save(pokemon2);


        List<Pokemon> pokemonList=pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList).size().isEqualTo(2);

    }

    @Test
    public void PokemonRepository_findById_Returns_SavedPokemon(){
        Pokemon pokemon=Pokemon.builder().name("bobo").type("land").build();
        Pokemon SavedPokemon=pokemonRepository.save(pokemon);

        Optional<Pokemon> findPokemon=pokemonRepository.findById(SavedPokemon.getId());

        Assertions.assertThat(findPokemon).isNotNull();
    }

    @Test
    public void PokemonRepository_findByType_NotNUll(){
        Pokemon pokemon=Pokemon.builder().name("abhishek").type("air").build();
        Pokemon savedPokemon=pokemonRepository.save(pokemon);

        Pokemon findPokemon=pokemonRepository.findByType("air").get();

        Assertions.assertThat(findPokemon).isNotNull();
    }

    @Test
    public void PokemonRepository_findByName_NotNUll(){
        Pokemon pokemon=Pokemon.builder().name("abhishek").type("air").build();
        Pokemon savedPokemon=pokemonRepository.save(pokemon);

        Pokemon findPokemon=pokemonRepository.findByName("abhishek").get();

        Assertions.assertThat(findPokemon).isNotNull();
    }

    @Test
    public void PokemonRepository_updatePokemon_Updated(){
        Pokemon pokemon=Pokemon.builder().name("abhishek").type("air").build();
        Pokemon savedPokemon=pokemonRepository.save(pokemon);

        Pokemon findPokemon=pokemonRepository.findById(savedPokemon.getId()).get();
        Assertions.assertThat(findPokemon).isNotNull();
        findPokemon.setName("Ruturaj");
        findPokemon.setType("Electric");

        Pokemon updatePokemon=pokemonRepository.save(findPokemon);

        Assertions.assertThat(updatePokemon).isNotNull();
        Assertions.assertThat(updatePokemon.getName()).isEqualTo("Ruturaj");
        Assertions.assertThat(updatePokemon.getType()).isEqualTo("Electric");
    }

    @Test
    public void PokemonRepository_Delete_isEmpty(){
        Pokemon pokemon=Pokemon.builder().name("Abhishek").type("air").build();
        pokemonRepository.save(pokemon);

        pokemonRepository.deleteById(pokemon.getId());

        Optional<Pokemon> deletedPokemon=pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(deletedPokemon).isEmpty();
    }



}
