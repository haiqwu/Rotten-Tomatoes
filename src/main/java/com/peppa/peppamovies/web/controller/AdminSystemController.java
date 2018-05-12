package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.MovieRankingData;
import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.EmailService;
import com.peppa.peppamovies.service.MovieReviewService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;


@Controller
public class AdminSystemController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MovieReviewService reviewService;




    @GetMapping("/admin_summary/ignore_user/{id}")
    public String ignoreReportedUser(@PathVariable Long id)
    {
        UserInfo user = userService.getUser(id);
        user.setReported(false);
        userService.updateUser( user.getUserID(), user );
        return "redirect:/admin_summary_mapping";

    }

    @GetMapping("/admin_summary/block_user/{id}")
    public String blockReportedUser(@PathVariable Long id)
    {
        UserInfo user = userService.getUser(id);

        user.setReported(false);
        user.setOfficially_blocked(true);

        userService.updateUser( user.getUserID(), user );
        return "redirect:/admin_summary_mapping";

    }
    @GetMapping("/admin_summary/remove_user/{id}")
    public String removeReportedUser(@PathVariable Long id)
    {
        UserInfo user = userService.getUser(id);
        
        List<MovieReview> movieReviews = user.getMovieReviews();
        for(MovieReview mr: movieReviews){
            reviewService.deleteReview(mr.getReviewID());
        }

        userService.deleteUser(id);
        //userService.updateUser( user.getUserID(), user );
        return "redirect:/admin_summary_mapping";

    }
    @GetMapping("/admin_summary/approve_critic/{id}")
    public String approveCriticApply(@PathVariable Long id)
    {
        UserInfo user = userService.getUser(id);
        user.setCritic(true);
        user.setApplying_critic(false);

        //user.setReported(false);
        //user.setOfficially_blocked(true);

        userService.updateUser( user.getUserID(), user );
        return "redirect:/admin_summary_mapping";

    }
    @GetMapping("/admin_summary/reject_critic/{id}")
    public String rejectCriticApply(@PathVariable Long id)
    {
        UserInfo user = userService.getUser(id);
        user.setApplying_critic(false);

        //user.setReported(false);
        //user.setOfficially_blocked(true);
        //userService.deleteUser(id);
        userService.updateUser( user.getUserID(), user );
        emailService.sendRejectCriticApplicationEmail(user);
        return "redirect:/admin_summary_mapping";

    }
    @GetMapping("/admin_summary/ignore_review/{id}")
    public String ignore_reviewReport(@PathVariable Long id)
    {
        //UserInfo user = userService.getUser(id);
        MovieReview movieReview = reviewService.getMovieReview(id);
        movieReview.setReported(false);
        //user.setApplying_critic(false);

        //user.setReported(false);
        //user.setOfficially_blocked(true);
        //userService.deleteUser(id);
        //userService.updateUser( user.getUserID(), user );
        reviewService.updateMovieReview( movieReview.getReviewID() , movieReview );
        return "redirect:/admin_summary_mapping";

    }
    @GetMapping("/admin_summary/remove_review/{id}")
    public String remove_review(@PathVariable Long id)
    {
        //UserInfo user = userService.getUser(id);
        MovieReview movieReview = reviewService.getMovieReview(id);

        //user.setReported(false);
        //user.setOfficially_blocked(true);
        //userService.deleteUser(id);
        reviewService.deleteReview(   id   );
        return "redirect:/admin_summary_mapping";

    }




    //@ModelAttribute("reported_user_list")
    @GetMapping("/admin_summary_mapping")
    public String loadAllAttributesForAdmin( Model model )
    {
        //System.out.println("yeah.");
        //System.out.println(userService.getReportedUsers());
        model.addAttribute("reported_user_list",userService.getReportedUsers());
        model.addAttribute("critic_applyers", userService.getCriticApplyers());

        List<MovieReview> movieReviews = reviewService.getReportedReviews();
        List<List<Object>> movieR = new ArrayList<>();
        for(MovieReview mr: movieReviews){
            List<Object> obj = new ArrayList<>();
            obj.add(mr);
            obj.add(movieService.getMovie(mr.getMovieID()));
            movieR.add(obj);
        }


        model.addAttribute("reported_review_list", movieR   );

        return "admin_summary";

    }

    @PostMapping("/admin_summary/add_movie")
    public String addMovie(@RequestParam String description,
                           @RequestParam String movie_images,
                           @RequestParam String movie_name,
                           @RequestParam String movie_poster,
                           @RequestParam String movie_trailer,
                           @RequestParam String movie_genres,
                           @RequestParam String secondary_id,
                           @RequestParam String box_office,
                           @RequestParam String movie_time,
                           @RequestParam String type,
                           @RequestParam String movie_date
                           )
    {
        MovieInfo m = new MovieInfo();
        m.setBriefIntro(   description);
        m.setMovieImages( movie_images );
        m.setMovieName     ( movie_name);
        m.setMoviePoster        (movie_poster);
        m.setMovieTrailers        ( movie_trailer);
        m.setGenres        ( movie_genres);
        m.setSecondaryID        (secondary_id);
        m.setBox_office(   Integer.parseInt(  box_office )  );
        m.setRuntimeMinutes(  Integer.parseInt(movie_time));
        m.setTitleType        ( type);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(movie_date);
            m.setReleasedDate(   date  );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //store to database
        movieService.saveMovie(m);
        return "redirect:/admin_summary_mapping";
    }













}
