package com.peppa.peppamovies.web.controller;

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
    @GetMapping("/opening_this_week")
    public String handleViewMoviesOpeningThisWeek(){
        return "movie_category_info";
    }
    @GetMapping("/top_box_office")
    public String handleViewTopBoxMovies(){
        return "movie_category_info";
    }
    @GetMapping("/comming_soon")
    public String handleViewComingSoonMovies(){
        return "movie_category_info";
    }
    @GetMapping("/certified_fresh_movies")
    public String handleViewCertifiedFreshesMovies(){
        return "movie_category_info";
    }

    public void handleTopMovies(){}
}
