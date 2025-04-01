package com.example.review_db;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);


    private String USER_CLIENT_URL = System.getenv("USER_CLIENT_URL");
    private String MOVIE_CLIENT_URL = System.getenv("MOVIE_CLIENT_URL");

    private final WebClient userClient;
    private final WebClient movieClient;

    private final ReviewRepository reviewRepository;


    public ReviewController(WebClient.Builder webClientBuilder, ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.userClient = webClientBuilder.baseUrl(USER_CLIENT_URL).build();
        this.movieClient = webClientBuilder.baseUrl(MOVIE_CLIENT_URL).build();
    }

@PostMapping
public ResponseEntity<Review> createReview(@RequestBody @Valid Review review) throws BadRequestException {
  return ResponseEntity.ok(reviewRepository.save(review));
}

@GetMapping("/{id}/review")//För Fredriks tjänst
public List<Review> getReviewsByMovieId(@PathVariable Long id) {
    return reviewRepository.findByMovieId(id);
}

    @GetMapping
    public Flux<ReviewResponse> getAllReviews() {
        return Flux.fromIterable(reviewRepository.findAll())
                .flatMap(review ->
                        getMovie(review.getMovieId())
                                .zipWith(getUser(review.getUserId()),
                                        (movie, user) -> new ReviewResponse(review, movie, user))
                );
    }

@GetMapping("/{id}")
public Mono<ReviewResponse> getReviewById(@PathVariable Long id) {
    return Mono.fromCallable(() -> reviewRepository.findById(id))
            .flatMap(optionalReview -> optionalReview
                    .map(review ->
                            getMovie(review.getMovieId())
                                    .zipWith(getUser(review.getUserId()),
                                            (movie, user) -> new ReviewResponse(review, movie, user))
                    )
                    .orElse(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found")))
            );
}


@GetMapping("/{id}/reviews")
public Flux<ReviewResponse> getReviewsOfMovie(@PathVariable Long id, Pageable pageable) {
    return Flux.fromIterable(reviewRepository.findByMovieId(id, pageable).getContent())
            .flatMap(review ->
                    getMovie(review.getMovieId())
                            .zipWith(getUser(review.getUserId()),
                                    (movie, user) -> new ReviewResponse(review, movie, user))
            );
}

@PutMapping("/{id}")
public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody @Valid Review updatedReview){
    Optional<Review> optionalReview = reviewRepository.findById(id);
    if (!optionalReview.isPresent()) {
        return ResponseEntity.notFound().build();
    }
    Review review = optionalReview.get();
    review.setMovieId(updatedReview.getMovieId());
    review.setUserId(updatedReview.getUserId());
    review.setTitle(updatedReview.getTitle());
    review.setContent(updatedReview.getContent());
    review.setRating(updatedReview.getRating());
    reviewRepository.save(review);
        return ResponseEntity.ok(review);
    }


@DeleteMapping("/{id}")
    public void deleteReviewById(@PathVariable Long id){
        reviewRepository.deleteById(id);
}


public Mono<Movie> getMovie(Long movieId) {
    logger.info("Fetching movie with ID: " + movieId);
    return movieClient.get()
            .uri("/movies/" + movieId)
            .retrieve()
            .bodyToMono(Map.class) // Mappa till en generisk Map
            .map(response -> (Map<String, Object>) response.get("movie")) // Extrahera "movie"-delen
            .map(movieMap -> {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(movieMap, Movie.class);
            })
            .doOnNext(movie -> logger.info("Fetched movie: " + movie))
            .onErrorResume(e -> {
                logger.error("Error fetching movie", e);
                return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
            });
}

    public Mono<User> getUser(Long userId) {
        return userClient.get()
                .uri("/users/" + userId)
                .retrieve()
                .bodyToMono(UserResponse.class) // Mappa direkt till UserResponse
                .map(UserResponse::getUser)
                .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));
    }
}
