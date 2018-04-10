package com.peppa.peppamovies.dao;

import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieInfo,Long> {}
