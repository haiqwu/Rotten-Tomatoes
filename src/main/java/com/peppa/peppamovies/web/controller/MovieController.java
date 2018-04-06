package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;



    public void handleTrendingMovie(){}
    public void handleSearchAction(){}
    public void handleShowMovieImage(){}
    public void handleShowActorInfo(){}

    @GetMapping("/movie/{id}")
    public String handleShowMovieInfo(@PathVariable Long id,Model model){
        //MovieInfo movie = movieService.getMovie(id);
        //String secondaryID = movie.getSecondaryID();

        model.addAttribute("movie",movieService.getMovie(id));

        return "movie_detail";
    }


    public void handleHoveringMovieCategory(){}
    public void handleViewMoviesOpeningThisWeek(){}
    public void handleViewTopBoxMovies(){}
    public void handleViewComingSoonMovies(){}
    public void handleViewCertifiedFreshesMovies(){}
    public void handleTopMovies(){}
}
