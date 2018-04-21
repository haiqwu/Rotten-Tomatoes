package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieInfo,Long> {
    @Query("select m from MovieInfo m where LOWER(m.movieName) like LOWER(?1) ")
    Page<MovieInfo> findByQuery(String query, Pageable pageable);
    @Query("select m from MovieInfo m")
    List<MovieInfo> findTop(Pageable pageable);
}
