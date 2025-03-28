package com.example.review_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createNewReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }


    public Review updateReview(Long id, Review updatedReview) {
        Optional<Review> existingReviewOptional = reviewRepository.findById(id);
        if (existingReviewOptional.isPresent()) {
            Review existingReview = existingReviewOptional.get();
            existingReview.setTitle(updatedReview.getTitle());
            existingReview.setContent(updatedReview.getContent());
            existingReview.setRating(updatedReview.getRating());

            return reviewRepository.save(existingReview);
        } else {
            return null;
        }
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
