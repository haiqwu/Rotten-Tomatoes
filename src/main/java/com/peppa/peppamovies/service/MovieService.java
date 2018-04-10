package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.MovieInfo;

public interface MovieService {
    MovieInfo saveMovie(MovieInfo movie);

    MovieInfo getMovie(Long id);

    MovieInfo updateMovie(Long id, MovieInfo movie);

    void deleteMovie(Long id);
}
