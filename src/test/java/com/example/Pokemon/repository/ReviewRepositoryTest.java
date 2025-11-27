package com.example.Pokemon.repository;


import com.example.Pokemon.models.Pokemon;
import com.example.Pokemon.models.Review;
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
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void ReviewRepository_SaveAll_SavedReview(){
        Review review=Review.builder().title("Title").content("content").stars(5).build();
        reviewRepository.save(review);

        Review savedReview=reviewRepository.findById(review.getId()).get();
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_Size(){
        Review review1=Review.builder().title("Title").content("content").stars(5).build();
        Review review2=Review.builder().title("Title").content("content").stars(5).build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        List<Review> reviewList=reviewRepository.findAll();

        Assertions.assertThat(reviewList).size().isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_findById_NotNull(){
        Review review=Review.builder().title("Title").content("content").stars(5).build();
        reviewRepository.save(review);

        Review findReview=reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(findReview).isNotNull();
    }

    @Test
    public void ReviewRepository_Update_Match(){
        Review review=Review.builder().title("Title").content("content").stars(5).build();
        reviewRepository.save(review);

        review.setTitle("New Title");
        review.setContent("New Content");;
        review.setStars(45);

        reviewRepository.save(review);

        Review updatedReview=reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(updatedReview.getTitle()).isEqualTo("New Title");
        Assertions.assertThat(updatedReview.getContent()).isEqualTo("New Content");
        Assertions.assertThat(updatedReview.getStars()).isEqualTo(45);
    }

    @Test
    public void ReviewRepository_Delete_isEmpty(){
        Review review=Review.builder().title("Title").content("content").stars(5).build();
        reviewRepository.save(review);

        reviewRepository.deleteById(review.getId());

        Optional<Review> deletedReview=reviewRepository.findById(review.getId());

        Assertions.assertThat(deletedReview).isEmpty();
    }

    @Test
    public void ReviewRepository_FindByPokemonId_Same() {
        // Arrange
        Pokemon pokemon = Pokemon.builder().name("Abhishek").type("Air").build();
        pokemonRepository.save(pokemon);

        Review review = Review.builder()
                .title("Title")
                .content("content")
                .stars(5)
                .pokemon(pokemon)
                .build();
        reviewRepository.save(review);

        // Act
        List<Review> reviews = reviewRepository.findByPokemonId(pokemon.getId());

        // Assert
        Assertions.assertThat(reviews).isNotEmpty();
        Assertions.assertThat(reviews).hasSize(1);

        Review findReview = reviews.get(0);
        Assertions.assertThat(findReview).isNotNull();
        Assertions.assertThat(findReview.getPokemon()).isEqualTo(pokemon);
    }

}
