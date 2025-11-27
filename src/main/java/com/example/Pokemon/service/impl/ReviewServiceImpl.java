package com.example.Pokemon.service.impl;

import com.example.Pokemon.dto.ReviewDto;
import com.example.Pokemon.exceptions.PokemonNotFoundException;
import com.example.Pokemon.exceptions.ReviewNotFoundException;
import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.models.Review;
import com.example.Pokemon.repository.PokemonRepository;
import com.example.Pokemon.repository.ReviewRepository;
import com.example.Pokemon.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

//    Constructor
    @Autowired
    ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository){
        this.reviewRepository=reviewRepository;
        this.pokemonRepository=pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto){
        Review review=maptoEntity(reviewDto);

        Pokemon pokemon=pokemonRepository.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("The pokemon for this reviews with id "+pokemonId+"was not found"));

        review.setPokemon(pokemon);

        Review savedReview=reviewRepository.save(review);

        return maptoDto(savedReview);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int pokemonId){
        List<Review> reviews=reviewRepository.findByPokemonId(pokemonId);
        return reviews.stream().map(review -> maptoDto(review)).collect(Collectors.toList());
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId){
        Pokemon pokemon=pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon with the id "+pokemonId+" for Review was not found."));

        Review review=reviewRepository.findById(reviewId).orElseThrow(()->  new ReviewNotFoundException("Review with id "+reviewId+" was not found"));

//        Not lets check if the review belongs to the given pokemon or not
        if(review.getPokemon().getId()!=pokemon.getId()){
            throw new ReviewNotFoundException("This review with id "+reviewId+" does not belong to the given pokemon with id "+ pokemonId);
        }

        return maptoDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto){
        Pokemon pokemon=pokemonRepository.findById(pokemonId).orElseThrow(()-> new PokemonNotFoundException("Pokemon with the id "+pokemonId+" for Review was not found."));

        Review review=reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Review with id "+reviewId+" was not found"));

//        Not lets check if the review belongs to the given pokemon or not
        if(review.getPokemon().getId()!=pokemon.getId()){
            throw new RuntimeException("This review with id "+reviewId+"does not belong to the given pokemon with id "+ pokemonId);
        }
        review.setContent(reviewDto.getContent());
        review.setTitle(reviewDto.getTitle());
        review.setStars(reviewDto.getStars());

        Review savedReview = reviewRepository.save(review);

        return maptoDto(savedReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId){
        Pokemon pokemon=pokemonRepository.findById(pokemonId).orElseThrow(()->new PokemonNotFoundException("Pokemon with the id "+pokemonId+" for Review was not found."));

        Review review=reviewRepository.findById(reviewId).orElseThrow(()->new ReviewNotFoundException("Review with id "+reviewId+" was not found"));

        if(review.getPokemon().getId()!=pokemon.getId()){
            throw new RuntimeException("This review with id "+reviewId+"does not belong to the given pokemon with id "+ pokemonId);
        }

        reviewRepository.delete(review);
    }

//    MAPPING
    private ReviewDto maptoDto(Review review){
        ReviewDto reviewDto=new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review maptoEntity(ReviewDto reviewDto){
        Review review=new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
