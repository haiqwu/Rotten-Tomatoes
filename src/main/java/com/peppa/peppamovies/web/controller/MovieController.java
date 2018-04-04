package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;







    public void handleTrendingMovie(){}
    public void handleSearchAction(){}
    public void handleShowMovieImage(){}
    public void handleShowActorInfo(){}
    public void handleShowMovieInfo(){}
    public void handleHoveringMovieCategory(){}
    public void handleViewMoviesOpeningThisWeek(){}
    public void handleViewTopBoxMovies(){}
    public void handleViewComingSoonMovies(){}
    public void handleViewCertifiedFreshesMovies(){}
    public void handleTopMovies(){}
}
