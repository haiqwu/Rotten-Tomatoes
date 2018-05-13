package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieReviewService {
    MovieReview saveMovieReview(MovieReview movieReview);

    MovieReview getMovieReview(Long id);

    MovieReview updateMovieReview(Long id, MovieReview movieReview);

    List<MovieReview> getReportedReviews();

    List<MovieReview> getAllReviews();

    void deleteReview(Long id);

    Page<MovieReview> listReviewsByTime(Pageable pageable);


}
