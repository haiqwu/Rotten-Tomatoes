package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReviewController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;







}
