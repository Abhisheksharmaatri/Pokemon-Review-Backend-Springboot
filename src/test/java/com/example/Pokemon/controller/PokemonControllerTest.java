package com.example.Pokemon.controller;


import com.example.Pokemon.controllers.PokemonController;
import com.example.Pokemon.dto.PokemonDto;
import com.example.Pokemon.dto.PokemonResponse;
import com.example.Pokemon.dto.ReviewDto;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.service.PokemonService;
import com.example.Pokemon.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private Pokemon pokemon;
    private PokemonDto pokemonDto;
    private ReviewDto reviewDto;
    private PokemonResponse pokemonResponse;

    @BeforeEach
    public void init(){
        this.pokemon= Pokemon.builder().name("Abhishek").type("air").build();
        this.pokemonDto= PokemonDto.builder().name("Abhishek").type("air").build();
        this.reviewDto=ReviewDto.builder().title("review").content("content").stars(5).build();
        this.pokemonResponse=PokemonResponse.builder().content(Arrays.asList(pokemonDto)).pageNumber(1).pageSize(1).totalElements(1).totalPages(1).last(true).build();

    }

    @Test
    public void PokemonController_GetAllPokemon_ListofPokemonDto() throws Exception{
        when(pokemonService.getAllPokemons()).thenReturn(Arrays.asList(pokemonDto));

        ResultActions response=mockMvc.perform(get("/api/pokemon"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name",CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type",CoreMatchers.is(pokemonDto.getType())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PokemonController_CreatePokemon_ResponseEntity_Created() throws Exception{
        given(pokemonService.createPokemon(ArgumentMatchers.any()))
                .willAnswer((invocation-> invocation.getArgument(0)));


        ResultActions response=mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void PokemonController_GetAllPokemon_PokemonResponsePage() throws Exception{
        when(pokemonService.getAllPokemonsPage(1,1)).thenReturn(pokemonResponse);

        ResultActions response=mockMvc.perform(get("/api/pokemon/page")
                .param("pageNumber","1")
                .param("pageSize","1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.last",CoreMatchers.is(pokemonResponse.getLast())))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    public void PokemonController_Update_Status_Ok_PokemonDto() throws Exception{
        when(pokemonService.updatePokemon(Mockito.any(),anyInt())).thenReturn(pokemonDto);

        ResultActions response=mockMvc.perform(put("/api/pokemon/"+1+"/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto))
                .accept(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type",CoreMatchers.is(pokemonDto.getType())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void PokemonController_Delete_Status_ok_String() throws Exception{
        doNothing().when(pokemonService).deletePokemon(1);
        ResultActions response=mockMvc.perform(delete("/api/pokemon/delete/{id}",1).param("id","1"));
        verify(pokemonService).deletePokemon(1);
    }

    @Test
    public void PokemonController_PokemonDetail_ResponseEntity_PokemonDto() throws Exception{
            when(pokemonService.getPokemon(anyInt())).thenReturn(pokemonDto);

            ResultActions response=mockMvc.perform(get("/api/pokemon/{id}",1).accept(MediaType.APPLICATION_JSON));

            response.andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name",CoreMatchers.is(pokemonDto.getName())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.type",CoreMatchers.is(pokemonDto.getType())))
                    .andDo(MockMvcResultHandlers.print());
    }

}
