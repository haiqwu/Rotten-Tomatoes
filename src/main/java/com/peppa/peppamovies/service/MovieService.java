package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.MovieInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {
    MovieInfo saveMovie(MovieInfo movie);

    MovieInfo getMovie(Long id);

    MovieInfo updateMovie(Long id, MovieInfo movie);

    void deleteMovie(Long id);

    Page<MovieInfo> listMovie(String query, Pageable pageable);
}
