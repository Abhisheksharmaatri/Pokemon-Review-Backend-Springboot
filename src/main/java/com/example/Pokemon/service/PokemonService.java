package com.example.Pokemon.service;

import com.example.Pokemon.dto.PokemonDto;
import com.example.Pokemon.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);

    List<PokemonDto> getAllPokemons();

//    Pagination
    PokemonResponse getAllPokemonsPage(int pageNumber, int pageSize);
    PokemonDto getPokemon(int id);

    PokemonDto updatePokemon( PokemonDto pokemonDto, int id);

    void deletePokemon(int id);
}
