package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<MovieInfo,Long> {
    @Query("select m from MovieInfo m where m.movieName like ?1")
    Page<MovieInfo> findByQuery(String query, Pageable pageable);
}
