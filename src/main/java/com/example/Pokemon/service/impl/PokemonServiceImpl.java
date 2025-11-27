package com.example.Pokemon.service.impl;


import com.example.Pokemon.dto.PokemonDto;
import com.example.Pokemon.dto.PokemonResponse;
import com.example.Pokemon.exceptions.PokemonNotFoundException;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.repository.PokemonRepository;
import com.example.Pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository){
        this.pokemonRepository=pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemondto){
        Pokemon pokemon=new Pokemon();
        pokemon.setName(pokemondto.getName());
        pokemon.setType(pokemondto.getType());

        Pokemon newPokemon=pokemonRepository.save(pokemon);

        PokemonDto pokemonResponse=new PokemonDto();
        pokemonResponse.setId(newPokemon.getId());
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());

        return pokemonResponse;
    }

    @Override
    public List<PokemonDto> getAllPokemons(){
        List<Pokemon> pokemons=pokemonRepository.findAll();
        return pokemons.stream().map(p -> maptoDto(p)).collect(Collectors.toList());
    }


    @Override
    public PokemonResponse getAllPokemonsPage(int pageNumber, int pageSize){
        Pageable pageable=  PageRequest.of(pageNumber, pageSize);
        Page<Pokemon> pokemons=pokemonRepository.findAll(pageable);
        List<Pokemon> pokemonList=pokemons.getContent();

        List<PokemonDto> content=pokemonList.stream().map(p->maptoDto(p)).collect(Collectors.toList());
        PokemonResponse pokemonResponse=new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNumber(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemon(int id){
        Pokemon pokemon=pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon with id: "+id+" was not found."));
        return maptoDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id){
        Pokemon pokemon=pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon with id "+id+" was not found"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updated=pokemonRepository.save(pokemon);
        return maptoDto(pokemon);
    }

    @Override
    public void deletePokemon(int id){
        Pokemon pokemon=pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon with id "+id+" was not found"));
        pokemonRepository.delete(pokemon);
    }


//    Helpers

    private PokemonDto maptoDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    private Pokemon maptoEntity(PokemonDto pokemonDto){
        Pokemon pokemon=new Pokemon();
        pokemon.setId(pokemonDto.getId());
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
}
