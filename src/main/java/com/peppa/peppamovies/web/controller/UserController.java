package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.MovieRankingData;
import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.MovieReviewService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieReviewService movieReviewService;
    MovieRankingData movieRankingData;

    @GetMapping("/")
    public String loginPage() {
        return "index";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session) {
        byte[] temp = password.getBytes();
        Byte[] passwdByte = toByteArr(temp);
        UserInfo user = userService.checkUser(username, passwdByte);
        if (user != null) {
            user.setPassW(null);
            session.setAttribute("user", user);
            return "index";
        } else {
            System.out.println("NOLogin");
            user.setUserName("*");
            session.setAttribute("user", user);
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/my_profile")
    public String handleProfileSummaryPage() {
        return "profile_template";
    }

    @PostMapping("/signup")
    public String handleSignUp(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String username,
                               @RequestParam String email, @RequestParam String password, @RequestParam String re_password) {
        UserInfo user = new UserInfo();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUserName(username);
        user.setEmail(email);
        if (password.equals(re_password)) {
            byte[] temp = password.getBytes();
            Byte[] passwdByte = toByteArr(temp);
            user.setPassW(passwdByte);
        } else {
        }
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("movie/movie_review_post")
    public String handlePostReview(@RequestParam String review_text) {
        MovieReview movieReview = new MovieReview();
        movieReview.setComment(review_text);
        movieReviewService.saveMovieReview(movieReview);
        return "redirect:/movie_detail";
    }

    @GetMapping("/licensing")
    public String handleShowLicenseInfo() {
        return "licensing";
    }

    @GetMapping("/profile_summary")
    public String handlePersonalProfileSummary() {
        return "profile_summary";
    }

    @GetMapping("/about")
    public String handleAboutPeppaTom() {
        return "about";
    }

    public void handleShowCriticInfo() {
    }

    public void handleOpenPersonalInfo() {
    }

    public void handleSucMesg() {
    }

    public void handleFailMesg() {
    }

    public void handleJoinNewsletter() {
    }

    public void handleFollowSocialMedia() {
    }

    public void handleChangePassword() {
    }

    public void handleUserHelpService() {
    }

    public void handleBusinessProposal() {
    }

    public void handleTomatometerRatingRule() {
    }

    public void handleCriticSearchBarFocus() {
    }

    public void handleCriticSearchBarTextChanging() {
    }

    public void handleCriticSearch() {
    }

    public void handleRTCriticHomeButton() {
    }

    public void handleCriticListButton() {
    }

    public void handleViewCriticGroups() {
    }

    public void handleViewLatestReviews() {
    }

    public void handleViewCriticsCriteria() {
    }

    /*helper functions*/
    @ModelAttribute("movie_lists")
    public ArrayList<ArrayList<MovieInfo>> loadMovieLists() {
        movieRankingData = new MovieRankingData();
        ArrayList<long[]> ranks = movieRankingData.loadMovieRankingData();
        int num_per_list = 8;
        //1. get top box office movies:
        long[] top_box_office_list = ranks.get(0);
        for (int i = 0; i < num_per_list; i++) {
            MovieInfo movie = movieService.getMovie(top_box_office_list[i]);
            movieRankingData.getTopBoxMovies().add(movie);
        }
        //2. get comming_soon movies:
        long[] comming_soon_list = ranks.get(1);
        for (int i = 0; i < num_per_list; i++) {
            MovieInfo movie = movieService.getMovie(comming_soon_list[i]);
            movieRankingData.getComingSoonMovies().add(movie);
        }
        //3. get opening_this_week movies:
        long[] opening_this_week_list = ranks.get(2);
        for (int i = 0; i < num_per_list; i++) {
            MovieInfo movie = movieService.getMovie(opening_this_week_list[i]);
            movieRankingData.getMoviesOpeningThisWeek().add(movie);
        }
        //4. certified_fresh_movies movies:
        long[] certified_fresh_movies_list = ranks.get(3);
        for (int i = 0; i < num_per_list; i++) {
            MovieInfo movie = movieService.getMovie(certified_fresh_movies_list[i]);
            movieRankingData.getCertifiedFreshMovies().add(movie);
        }
        ArrayList<ArrayList<MovieInfo>> loaded_movie_lists = new ArrayList<>();
        loaded_movie_lists.add(movieRankingData.getMoviesOpeningThisWeek());
        loaded_movie_lists.add(movieRankingData.getTopBoxMovies());
        loaded_movie_lists.add(movieRankingData.getCertifiedFreshMovies());
        loaded_movie_lists.add(movieRankingData.getComingSoonMovies());
        return loaded_movie_lists;
    }

    public Byte[] toByteArr(byte[] bytes) {
        Byte[] byteObjects = new Byte[bytes.length];
        int i = 0;
        for (byte b : bytes)
            byteObjects[i++] = b;
        return byteObjects;
    }
}
