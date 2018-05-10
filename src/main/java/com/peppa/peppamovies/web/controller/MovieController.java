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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movie/{id}")
    public String handleShowMovieInfo(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("movie", movieService.getMovie(id));
        session.setAttribute("movie", movieService.getMovie(id));
        return "movie_detail";
    }

    @GetMapping("/opening_this_week")
    public String handleViewMoviesOpeningThisWeek(@PageableDefault(size = 8, sort = {"releasedDate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.JANUARY, 1);
        Date date = cal.getTime();
        Page<MovieInfo> movies = movieService.listOpeningMovie(date, pageable);
        model.addAttribute("page", movies);
        model.addAttribute("link", "/opening_this_week");
        return "movie_category_info";
    }

    @GetMapping("/top_box_office")
    public String handleViewTopBoxMovies(@PageableDefault(size = 8, sort = {"totalRate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<MovieInfo> movies = movieService.listTopMovie(pageable);
        model.addAttribute("page", movies);
        model.addAttribute("link", "/top_box_office");
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

    @RequestMapping("/search")
    public String handleSearchAction(@PageableDefault(size = 8, sort = {"movieName"},
            direction = Sort.Direction.DESC) Pageable pageable,
                                     String query, Model model, HttpSession session) {
        if (query == null) {
            Object queryItem = session.getAttribute("queryItem");
            query = ((String) queryItem);
        }
        query = query.trim();
        query = query.replaceAll("\\s+", " ");
        Page<MovieInfo> queryResult = movieService.listMovie("%" + query + "%", pageable);
        model.addAttribute("page", queryResult);
        model.addAttribute("query", query);
        session.setAttribute("queryItem", query);
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
