package com.example.review_db;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private String USER_CLIENT_URL = System.getenv("USER_CLIENT_URL");
    private String MOVIE_CLIENT_URL = System.getenv("MOVIE_CLIENT_URL");

    private final WebClient userClient;
    private final WebClient movieClient;

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    public ReviewController(WebClient.Builder webClientBuilder, ReviewRepository reviewRepository, ReviewService reviewService) {
        this.reviewRepository = reviewRepository;
        this.reviewService = reviewService;
        this.userClient = webClientBuilder.baseUrl(USER_CLIENT_URL).build();
        this.movieClient = webClientBuilder.baseUrl(MOVIE_CLIENT_URL).build();
    }

    @PostMapping
    public void createReview(@RequestBody Review review){
        reviewService.createNewReview(review);
    }

@GetMapping
public ResponseEntity<List<Review>> getAllReviews(){
        return ResponseEntity.ok(reviewService.getReviews());
}

@GetMapping("/{id}")
public ResponseEntity<Review> getReviewById(@PathVariable Long id){
        return ResponseEntity.ok(reviewService.getReview(id).orElse(null));
}
@GetMapping("/{id}/reviews")
public Page<Review> getReviewsByMovieId(@PathVariable Long id, Pageable pageable){
        return reviewService.getReviewsOfMovie(id,pageable);
}

@PutMapping("/{id}")
public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview){
        Review review = reviewService.updateReview(id, updatedReview);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
}

@DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable Long id){
        reviewService.deleteReview(id);
}

}
