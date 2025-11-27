package com.example.Pokemon.service;


import com.example.Pokemon.dto.ReviewDto;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.models.Review;
import com.example.Pokemon.repository.PokemonRepository;
import com.example.Pokemon.repository.ReviewRepository;
import com.example.Pokemon.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    public void ReviewService_CreateReview_ReviewDto(){
        ReviewDto reviewDto=ReviewDto.builder().title("title").content("content").stars(5).build();
        Review review=Review.builder().title("title").content("content").build();
        Pokemon pokemon= Pokemon.builder().name("Abhishek").type("String").build();

        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        ReviewDto savedReviewDto=reviewService.createReview(1, reviewDto);

        Assertions.assertThat(savedReviewDto).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewsByPokemonId_ListReviewDto(){
        Pokemon pokemon= Pokemon.builder().name("Abhishek").type("String").id(1).build();
        ReviewDto reviewDto=ReviewDto.builder().title("title").content("content").stars(5).build();
        Review review=Review.builder().title("title").content("content").pokemon(pokemon).build();


        when(reviewRepository.findByPokemonId(1)).thenReturn(List.of(review));

        List<ReviewDto> findReview=reviewService.getReviewsByPokemonId(1);

        Assertions.assertThat(findReview).isNotEmpty();
    }

    @Test
    public void ReviewService_GetReviewById_ReviewDto(){
        Pokemon pokemon= Pokemon.builder().name("Abhishek").type("String").id(1).build();
        ReviewDto reviewDto=ReviewDto.builder().title("title").content("content").stars(5).build();
        Review review=Review.builder().title("title").content("content").pokemon(pokemon).build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(1)).thenReturn(Optional.ofNullable(review));

        ReviewDto findReview=reviewService.getReviewById(1,1);

        Assertions.assertThat(findReview).isNotNull();
    }

    @Test
    public void ReviewService_UpdateReview_ReviewDto(){
        Pokemon pokemon= Pokemon.builder().name("Abhishek").type("String").id(1).build();
        ReviewDto reviewDto=ReviewDto.builder().title("title").content("content").stars(5).build();
        Review review=Review.builder().title("title").content("content").pokemon(pokemon).build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(1)).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto updatedReview=reviewService.updateReview(1,1, reviewDto);

        Assertions.assertThat(updatedReview).isNotNull();
    }

    @Test
    public void ReviewService_deleteReview_Void(){
        Pokemon pokemon= Pokemon.builder().name("Abhishek").type("String").id(1).build();
        ReviewDto reviewDto=ReviewDto.builder().title("title").content("content").stars(5).build();
        Review review=Review.builder().title("title").content("content").pokemon(pokemon).build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(1)).thenReturn(Optional.ofNullable(review));
//        when(reviewRepository.delete(Mockito.any(Review.class))).thenReturn(void);

        assertAll(()-> reviewService.deleteReview(1,1));


    }
}
