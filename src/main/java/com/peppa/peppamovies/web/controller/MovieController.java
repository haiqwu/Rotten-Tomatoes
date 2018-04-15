package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/{id}")
    public String handleShowMovieInfo(@PathVariable Long id, Model model) {
        //MovieInfo movie = movieService.getMovie(id);
        //String secondaryID = movie.getSecondaryID();

        model.addAttribute("movie", movieService.getMovie(id));

        return "movie_detail";
    }

    @GetMapping("/opening_this_week")
    public String handleViewMoviesOpeningThisWeek() {
        return "movie_category_info";
    }

    @GetMapping("/top_box_office")
    public String handleViewTopBoxMovies() {
        return "movie_category_info";
    }

    @GetMapping("/comming_soon")
    public String handleViewComingSoonMovies() {
        return "movie_category_info";
    }

    @GetMapping("/certified_fresh_movies")
    public String handleViewCertifiedFreshesMovies() {
        return "movie_category_info";
    }

    @PostMapping("/search")
    public String handleSearchAction(@PageableDefault(size = 8, sort ={"movieName"},
                                     direction = Sort.Direction.DESC)Pageable pageable,
                                     @RequestParam String query, Model model) {
        query = query.trim();
        query = query.replaceAll("\\s+"," ");
        Page<MovieInfo> queryResult = movieService.listMovie("%"+query+"%", pageable);
        model.addAttribute("page",queryResult);
        model.addAttribute("query",query);
        return "search_results";
    }

    public void handleTopMovies() {
    }

    public void handleTrendingMovie() {
    }

    public void handleShowMovieImage() {
    }

    public void handleShowActorInfo() {
    }

    public void handleHoveringMovieCategory() {
    }
}
