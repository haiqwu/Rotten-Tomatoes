package com.peppa.peppamovies.dao;


import com.peppa.peppamovies.model.MovieReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieReviewRepository extends JpaRepository<MovieReview,Long> {
}
