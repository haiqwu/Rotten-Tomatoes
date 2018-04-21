package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.MovieReview;

public interface MovieReviewService {
    MovieReview saveMovieReview(MovieReview movieReview);

    MovieReview getMovieReview(Long id);

    MovieReview updateMovieReview(Long id, MovieReview movieReview);
}
