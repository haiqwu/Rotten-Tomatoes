package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.*;
import com.peppa.peppamovies.service.*;
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
    @Autowired
    private TVService tvService;


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
            MovieInfo mi = movieService.getMovie(mr.getMovieID());
            if(user.isCritic()){
                mi.setCriticRate((mi.getCriticRate()*mi.getCriticRateCount())-mr.getRate());
                mi.setCriticRateCount(mi.getCriticRateCount()-1);
                mi.setCriticRate(mi.getCriticRate()/mi.getCriticRateCount());
            }else{
                mi.setAudianceRate((mi.getAudianceRate()*mi.getAudiRateCount())-mr.getRate());
                mi.setAudiRateCount(mi.getAudiRateCount()-1);
                mi.setAudianceRate(mi.getAudianceRate()/mi.getAudiRateCount());
            }
            mi.setTotalRate((mi.getCriticRate()+mi.getAudianceRate())/2);
            movieService.updateMovie(mi.getMovieID(),mi);
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
        MovieInfo mi = movieService.getMovie(movieReview.getMovieID());
        if(movieReview.getUser().isCritic()){
            mi.setCriticRate((mi.getCriticRate()*mi.getCriticRateCount())-movieReview.getRate());
            mi.setCriticRateCount(mi.getCriticRateCount()-1);
            mi.setCriticRate(mi.getCriticRate()/mi.getCriticRateCount());
        }else{
            mi.setAudianceRate((mi.getAudianceRate()*mi.getAudiRateCount())-movieReview.getRate());
            mi.setAudiRateCount(mi.getAudiRateCount()-1);
            mi.setAudianceRate(mi.getAudianceRate()/mi.getAudiRateCount());
        }
        mi.setTotalRate((mi.getCriticRate()+mi.getAudianceRate())/2);
        movieService.updateMovie(mi.getMovieID(),mi);
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
    @PostMapping("/admin_summary/add_tv")
    public String addTV(
                           @RequestParam String description ,
                           @RequestParam String tv_images ,
                           @RequestParam String tv_name ,
                           @RequestParam String  tv_poster,
                           @RequestParam String  secondaryid,
                           @RequestParam String  genres,
                           @RequestParam String  runtime_minutes,
                           @RequestParam String  title_type,
                           @RequestParam String released_date ,
                           @RequestParam String season
                        )
    {
        TVInfo t = new TVInfo();
        //MovieInfo m = new MovieInfo();
        t.setBriefIntro( description);
        t.setTvImages( tv_images );
        t.setTvName( tv_name );
        t.setRuntimeMinutes(  Integer.parseInt( runtime_minutes  ) );
        t.setTvPoster( tv_poster );
        t.setSecondaryID( secondaryid  );
        t.setGenres( genres );
        t.setSeason( Integer.parseInt( season)  );
        t.setTitleType( title_type );


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(released_date);
            t.setReleasedDate(   date  );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //store to database
        tvService.saveTV(t);
        //movieService.saveMovie(m);
        return "redirect:/admin_summary_mapping";
    }












}
