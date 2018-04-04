package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.service.MovieReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class MovieReviewController {

    @Autowired
    private MovieReviewService movieReviewService;


    @GetMapping("/movie_detail")
    public String handleMovieDetail(){
        return "movie_detail";
    }


    @PostMapping("/movie_review_post")
    public String handlePostReview(@RequestParam String review_text, HttpSession session,
                                   RedirectAttributes attributes){
        MovieReview movieReview = new MovieReview();
        movieReview.setComment(review_text);
        //movie name by id? to set the movie to review



        movieReviewService.saveMovieReview(movieReview);
        return "redirect:/movie_detail";
    }
}
