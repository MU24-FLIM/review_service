package com.example.review_db;

import java.util.List;

public class ReviewResponse {

    private Review review;
    private Movie movie;
    private User user;

    public ReviewResponse(Review review, Movie movie, User user) {
        this.review = review;
        this.movie = movie;
        this.user = user;
    }

    public ReviewResponse() {
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
