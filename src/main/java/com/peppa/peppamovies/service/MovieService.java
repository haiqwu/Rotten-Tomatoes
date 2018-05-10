package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface MovieService {
    MovieInfo saveMovie(MovieInfo movie);

    MovieInfo getMovie(Long id);

    MovieInfo updateMovie(Long id, MovieInfo movie);

    Page<MovieInfo> listMovie(String query, Pageable pageable);

    Page<MovieInfo> listOpeningMovie(Date date, Pageable pageable);

    Page<MovieInfo> listTopMovie(Pageable pageable);

    List<MovieInfo> listOpeningMovieL(Date date);

    List<MovieInfo> listTopMovieL();
}
