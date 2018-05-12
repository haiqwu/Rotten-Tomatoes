package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.MovieReview;

import java.util.List;

public interface MovieReviewService {
    MovieReview saveMovieReview(MovieReview movieReview);

    MovieReview getMovieReview(Long id);

    MovieReview updateMovieReview(Long id, MovieReview movieReview);

    List<MovieReview> getReportedReviews();

    List<MovieReview> getAllReviews();

    void deleteReview(Long id);

}
