package com.peppa.peppamovies.dao;


import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieReviewRepository extends JpaRepository<MovieReview,Long> {

    @Query("select m from MovieReview m where m.reported = 1 ")
    List<MovieReview> getAllReportedReviews();

    @Query("select m from MovieReview m")
    List<MovieReview> getAllReviews();


    @Query("select m from MovieReview m where m.is_critic_review = 1 order by m.dayCommented desc ")
    Page<MovieReview> getAllCriticReviews(Pageable pageable);
}
