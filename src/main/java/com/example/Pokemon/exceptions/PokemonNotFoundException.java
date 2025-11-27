package com.example.Pokemon.exceptions;

public class PokemonNotFoundException extends RuntimeException{
    private static final long serialVersionId=1;

    public PokemonNotFoundException(String message){
        super(message);
    }
}
