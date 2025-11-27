package com.example.Pokemon.controller;


import com.example.Pokemon.controllers.ReviewController;
import com.example.Pokemon.dto.PokemonDto;
import com.example.Pokemon.dto.ReviewDto;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.models.Review;
import com.example.Pokemon.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private Review review;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;
    private Pokemon pokemon;

    @BeforeEach
    public void init(){
        this.pokemon= Pokemon.builder().name("Abhishek").type("air").build();
        this.pokemonDto= PokemonDto.builder().name("Abhishek").type("air").build();
        this.reviewDto=ReviewDto.builder().id(1).title("review").content("content").stars(5).build();
        this.review=Review.builder().id(1).title("review").content("content").stars(5).build();
    }

    @Test
    public void ReviewController_Create_ResponseEntity_ReviewDto() throws Exception{
        when(reviewService.createReview(anyInt(), Mockito.any())).thenReturn(reviewDto);

        ResultActions response=mockMvc.perform(post("/api/pokemon/1/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReviewController_GetReviewByPokemonId_List_ReviewDto() throws Exception{
        when(reviewService.getReviewsByPokemonId(Mockito.anyInt())).thenReturn(Arrays.asList(reviewDto));

        ResultActions response=mockMvc.perform(get("/api/pokemon/1/reviews").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(reviewDto)));

        response
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].content", CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].stars", CoreMatchers.is(reviewDto.getStars())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReviewController_getReviewById_ResponseEntity_ReviewDto() throws Exception{
        when(reviewService.getReviewById(anyInt(), anyInt())).thenReturn(reviewDto);

        ResultActions response=mockMvc.perform(get("/api/pokemon/1/reviews/1"));

        response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars",CoreMatchers.is(reviewDto.getStars())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReviewController_updateReview_ResponseEntity_ReviewDto() throws Exception{
        when(reviewService.updateReview(anyInt(), anyInt(), Mockito.any())).thenReturn(reviewDto);

        ResultActions response=mockMvc.perform(put("/api/pokemon/1/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDto))
                .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title",CoreMatchers.is(reviewDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",CoreMatchers.is(reviewDto.getContent())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars",CoreMatchers.is(reviewDto.getStars())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void ReviewController_deleteReview_ResponseEntity_String() throws Exception{
        doNothing().when(reviewService).deleteReview(anyInt(),anyInt());

        ResultActions response=mockMvc.perform(delete("/api/pokemon/1/reviews/1"));

        response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",CoreMatchers.is("The review was successfully deleted.")))
                .andDo(MockMvcResultHandlers.print());
    }

}
