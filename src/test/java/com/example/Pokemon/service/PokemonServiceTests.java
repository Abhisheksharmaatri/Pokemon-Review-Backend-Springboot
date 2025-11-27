package com.example.Pokemon.service;


import com.example.Pokemon.dto.PokemonDto;
import com.example.Pokemon.dto.PokemonResponse;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.repository.PokemonRepository;
import com.example.Pokemon.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTests {
    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @Test
    public void PokemonService_Create_PokemonDto(){
        Pokemon pokemon=Pokemon.builder().name("Abhishek").type("air").build();
        PokemonDto pokemonDto=PokemonDto.builder().name("Abhishek").type("air").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemonDto=pokemonService.createPokemon(pokemonDto);

        Assertions.assertThat(savedPokemonDto).isNotNull();
    }

    @Test
    public void PokemonService_GetAllPokemonsPage_ListPokemonDto(){
        Page<Pokemon> pokemons=Mockito.mock(Page.class);

        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savePokemon=pokemonService.getAllPokemonsPage(1,10);

        Assertions.assertThat(savePokemon).isNotNull();
    }

    @Test
    public void PokemonService_getPokemon_PokemonDto(){
        Pokemon pokemon=Pokemon.builder().name("Abhishek").type("air").build();
        PokemonDto pokemonDto=PokemonDto.builder().name("Abhishek").type("air").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto findPokemonDto=pokemonService.getPokemon(1);

        Assertions.assertThat(pokemonDto).isNotNull();
    }

    @Test
    public void PokemonService_updatePokemon_PokemonDto(){
        Pokemon pokemon=Pokemon.builder().name("Abhishek").type("air").build();
        PokemonDto pokemonDto=PokemonDto.builder().name("updated").type("updated").build();

        Pokemon updatedPokemon=Pokemon.builder().name("updated").type("updated").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(updatedPokemon);
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto updatePokemon=pokemonService.updatePokemon(pokemonDto, 1);

        Assertions.assertThat(updatePokemon).isNotNull();
        Assertions.assertThat(updatePokemon.getName()).isEqualTo(updatedPokemon.getName());
        Assertions.assertThat(updatePokemon.getType()).isEqualTo(updatedPokemon.getType());
    }

    @Test
    public void PokemonService_deletePokemon_void(){
        Pokemon pokemon=Pokemon.builder().name("Abhishek").type("air").build();
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        assertAll(()-> pokemonService.deletePokemon(1));
    }
}
