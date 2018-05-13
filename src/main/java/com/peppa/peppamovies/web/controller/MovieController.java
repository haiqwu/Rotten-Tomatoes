package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.TVInfo;
import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.MovieReviewService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.TVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieReviewService movieReviewService;

    @Autowired
    private TVService tvService;

    @GetMapping("/movie/{id}")
    public String handleShowMovieInfo(@PathVariable Long id, Model model, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        List<MovieReview> moviesAllReviews = movieReviewService.getAllReviews();
        List<MovieReview> movieAllReviewsByCritic = new ArrayList<>();
        List<MovieReview> movieAllReviewsByAudiance = new ArrayList<>();
        if(user != null){
            List<MovieReview> movieReviews = user.getMovieReviews();
            for(MovieReview mr: movieReviews){
                if(mr.getMovieID().equals(id)){
                    model.addAttribute("movieReview", mr.getComment());
                    model.addAttribute("RateReview", mr.getRate()/20);
                    break;
                }else{
                    model.addAttribute("movieReview", null);
                }
            }
        }else{
            model.addAttribute("movieReview", null);
        }
        for(MovieReview mr: moviesAllReviews){
            if(mr.getMovieID().equals(id) && mr.getUser().isCritic()){
                movieAllReviewsByCritic.add(mr);
            }else if(mr.getMovieID().equals(id)&& !(mr.getUser().isCritic())){
                movieAllReviewsByAudiance.add(mr);
            }
        }
        model.addAttribute("movie", movieService.getMovie(id));
        model.addAttribute("reviewsByCritic", movieAllReviewsByCritic);
        model.addAttribute("reviewsByAudiance", movieAllReviewsByAudiance);
        session.setAttribute("movie", movieService.getMovie(id));
        return "movie_detail";
    }

    @GetMapping("/tv/{id}")
    public String handleShowTVInfo(@PathVariable Long id, Model model, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        model.addAttribute("tv", tvService.getTV(id));
        session.setAttribute("tvS", tvService.getTV(id));
        return "tv_detail";
    }

    @GetMapping("/opening_this_week")
    public String handleViewMoviesOpeningThisWeek(@PageableDefault(size = 8, sort = {"releasedDate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dd = cal.get(Calendar.DATE);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int dayEnd = -1;
        int dayStart = -1;
        switch (day) {
            case Calendar.SUNDAY:
                dayStart = dd - 6;
                dayEnd = dd;
            case Calendar.MONDAY:
                dayStart = dd;
                dayEnd = dd + 6;
            case Calendar.TUESDAY:
                dayStart = dd - 1;
                dayEnd = dd + 5;
            case Calendar.WEDNESDAY:
                dayStart = dd - 2;
                dayEnd = dd + 4;
            case Calendar.THURSDAY:
                dayStart = dd - 3;
                dayEnd = dd + 3;
            case Calendar.FRIDAY:
                dayStart = dd - 4;
                dayEnd = dd + 2;
            case Calendar.SATURDAY:
                dayStart = dd - 5;
                dayEnd = dd + 1;
        }
        Calendar calStart = Calendar.getInstance();
        calStart.set(year, month, dayStart);
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(year, month, dayEnd);
        Date dateStart = calStart.getTime();
        Date dateEnd = calEnd.getTime();
        Page<MovieInfo> movies = movieService.listOpeningMovie(dateStart, dateEnd, pageable);
        model.addAttribute("page", movies);
        model.addAttribute("link", "/opening_this_week");
        model.addAttribute("boolean", "123");
        return "movie_category_info";
    }

    @GetMapping("/top_box_office")
    public String handleViewTopBoxMovies(@PageableDefault(size = 8, sort = {"totalRate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<MovieInfo> movies = movieService.listTopMovie(pageable);
        model.addAttribute("page", movies);
        model.addAttribute("link", "/top_box_office");
        model.addAttribute("boolean", "123");
        return "movie_category_info";
    }

    @GetMapping("/comming_soon")
    public String handleViewComingSoonMovies(@PageableDefault(size = 8, sort = {"releasedDate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Date date = new Date();
        Page<MovieInfo> movies = movieService.listComing(date, pageable);
        model.addAttribute("page", movies);
        model.addAttribute("link", "/comming_soon");
        model.addAttribute("boolean", "123");
        return "movie_category_info";
    }

    @GetMapping("/top_rated_tv_shows")
    public String handleTopRatedTVShows(@PageableDefault(size = 8, sort = {"totalRate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<TVInfo> tvs = tvService.listTopRatedTV(pageable);
        model.addAttribute("page", tvs);
        model.addAttribute("link", "/top_rated_tv_shows");
        model.addAttribute("boolean", null);
        return "movie_category_info";
    }

    @GetMapping("/certified_fresh_tv_shows")
    public String handleCertifiedFreshTVShows(@PageableDefault(size = 8, sort = {"criticRate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Date date = new Date();
        Page<TVInfo> tvs = tvService.listCriticTopRatedTV(pageable);
        model.addAttribute("page", tvs);
        model.addAttribute("link", "/certified_fresh_tv_shows");
        model.addAttribute("boolean", null);
        return "movie_category_info";
    }

//    @GetMapping("/certified_fresh_movies")
//    public String handleViewCertifiedFreshesMovies() {
//        return "movie_category_info";
//    }

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

    @GetMapping("/edit_movie_detail/{id}")
    public String handleEditMovieDetail(@PathVariable Long id,Model model){
        System.out.println("movieId: "+ id);
        model.addAttribute( "movie_tbe", movieService.getMovie(id));
        return "edit_movie_detail";
    }

    @GetMapping("/report_review/{id}")
    public String handleReportReview(@PathVariable Long id)
    {
        MovieReview movieReview = movieReviewService.getMovieReview(id);
        movieReview.setReported(true);
        movieReviewService.updateMovieReview( id,  movieReview );
        return "redirect:/";//need a page!!!!!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    }

    @PostMapping("/edit_movie_content/{id}")
    public String handleEditMovieDetailForm(@PathVariable Long id,
                                            @RequestParam String description,
                                            @RequestParam String movie_name,
                                            @RequestParam String movie_genres,
                                            @RequestParam String runtime_minutes,
                                            @RequestParam String released_date,
                                            @RequestParam String box_office,
                                            @RequestParam String secondaryid,
                                            @RequestParam String movie_images,
                                            @RequestParam String movie_poster,
                                            @RequestParam String movie_trailers
                                            )
    {
        MovieInfo m = movieService.getMovie(id);

        m.setBriefIntro( description);
        m.setMovieName(movie_name);
        m.setSecondaryID( secondaryid  );
        m.setGenres( movie_genres);
        m.setMoviePoster( movie_poster );
        m.setMovieTrailers(  movie_trailers );
        m.setMovieImages(movie_images);
        m.setRuntimeMinutes( Integer.parseInt(  runtime_minutes ) );
        m.setBox_office( Integer.parseInt( box_office) );

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(released_date);
            m.setReleasedDate(   date  );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        movieService.updateMovie(  id , m  );
        return "redirect:/admin_summary_mapping";
    }

    @GetMapping("/delete_movie_detail/{id}")
    public String handleDeleteMovieDetail(@PathVariable Long id,Model model){
        System.out.println("movieId: "+ id);
        model.addAttribute("movie_tbd_id",id);
        return "deleteMovieDetail";
    }

    @PostMapping("/delete_movie_detail2/{id}")
    public String del_movie_by_admin(@PathVariable Long id)
    {
        movieService.deleteMovie(id);
        return "redirect:/admin_summary_mapping";
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
