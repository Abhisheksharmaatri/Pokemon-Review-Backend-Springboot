package com.example.Pokemon.controllers;


import com.example.Pokemon.dto.PokemonDto;
import com.example.Pokemon.dto.PokemonResponse;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PokemonController {
    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService){
        this.pokemonService=pokemonService;
    }

    @GetMapping("pokemon")
    public ResponseEntity<List<PokemonDto>> getAllPokemons(){

        return new ResponseEntity<>(pokemonService.getAllPokemons(), HttpStatus.OK);
    }

    @GetMapping("pokemon/page")
    public ResponseEntity<PokemonResponse> getAllPokemonsPage(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
        ){
            return new ResponseEntity<>(pokemonService.getAllPokemonsPage(pageNumber, pageSize), HttpStatus.OK);
        }

    @GetMapping("pokemon/{id}")
    public ResponseEntity<PokemonDto> pokemonDetail(@PathVariable int id){
        return ResponseEntity.ok(pokemonService.getPokemon(id));
    }

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto){
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
//        System.out.println(pokemon.getName());
//        System.out.println(pokemon.getType());
//        return new ResponseEntity<>(pokemon, HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@RequestBody PokemonDto pokemonDto, @PathVariable("id") int id){
        PokemonDto udpatePokemonDto=pokemonService.updatePokemon(pokemonDto, id);
        return ResponseEntity.ok(udpatePokemonDto);
    }

//    @DeleteMapping("pokemon/{id}/delete")
//    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id){
//        System.out.println(id);
//        return ResponseEntity.ok("Pokemon Deleted Successfully");
//    }

    @DeleteMapping("pokemon/delete/{id}")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int id){
        pokemonService.deletePokemon(id);
        return new ResponseEntity<>("Pokemon with id: "+id+" was deleted.", HttpStatus.OK);
    }
}
