package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.MovieRankingData;
import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.MovieReviewService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import com.peppa.peppamovies.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieReviewService movieReviewService;
    private MovieRankingData movieRankingData;

    @GetMapping("/")
    public String loginPage() {
        //PropertiesManager props = PropertiesManager.getPropertiesManager();
        //System.out.println(  props.getProperty(FIRST_PROP.toString()   )  );
        return "index";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        UserInfo user = userService.checkUser(username, MD5Util.mdCode(password));
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("found", true);
            return "redirect:" + referer;
        } else {
            session.setAttribute("found", false);
            System.out.println("NOLogin");
            return "redirect:" + referer;
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        session.removeAttribute("user");
        session.removeAttribute("found");
        return "redirect:" + referer;
    }

    @GetMapping("/my_profile")
    public String handleProfileSummaryPage() {
        return "profile_template";
    }

    @PostMapping("/signup")
    public String handleSignUp(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String username,
                               @RequestParam String email, @RequestParam String password_signup, @RequestParam String re_password,
                               HttpServletRequest request, HttpSession session) {
        String referer = request.getHeader("Referer");
        if (userService.checkUsername(username)) { // not exist
            session.setAttribute("exist", false);
            UserInfo user = new UserInfo();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setUserName(username);
            user.setEmail(email);
            String hashedPassword = MD5Util.mdCode(password_signup);
            user.setPassW(hashedPassword);
            userService.saveUser(user);
        } else { // already exist
            session.setAttribute("exist", true);
        }
        return "redirect:" + referer;
    }

    @GetMapping("/movie/{id}/wants_to_see")
    public String handleWantsToSee(HttpSession session) {
        UserInfo currentUser = (UserInfo) session.getAttribute("user");
        MovieInfo currentMovie = (MovieInfo) session.getAttribute("movie");
        if (currentUser != null) {
            boolean isDupli = false;
            List<MovieInfo> wts = currentUser.getWantsToSeeList();
            for(int i=0; i<wts.size(); i++){
                if(wts.get(i).getMovieID().equals(currentMovie.getMovieID())){
                    isDupli = true;
                    break;
                }
            }
            if(!isDupli){
                currentUser.getWantsToSeeList().add(currentMovie);
                userService.updateUser(currentUser.getUserID(), currentUser);
            }
        } else {
            System.out.println("User not log in");
        }
        return "redirect:/movie/" + currentMovie.getMovieID();
    }

    @GetMapping("/movie/{id}/not_interested")
    public String handleNotInterested(HttpSession session) {
        UserInfo currentUser = (UserInfo) session.getAttribute("user");
        MovieInfo currentMovie = (MovieInfo) session.getAttribute("movie");
        if (currentUser != null) {
            boolean isDupli = false;
            List<MovieInfo> wts = currentUser.getNotInterestedList();
            for(int i=0; i<wts.size(); i++){
                if(wts.get(i).getMovieID().equals(currentMovie.getMovieID())){
                    isDupli = true;
                    break;
                }
            }
            if(!isDupli){
                currentUser.getNotInterestedList().add(currentMovie);
                userService.updateUser(currentUser.getUserID(), currentUser);
            }
        } else {
            System.out.println("User not log in");
        }
        return "redirect:/movie/" + currentMovie.getMovieID();
    }

    @PostMapping("/movie/{id}/post")
    public String handlePost(@RequestParam String review_text, HttpSession session) {
        UserInfo currentUser = (UserInfo) session.getAttribute("user");
        MovieInfo currentMovie = (MovieInfo) session.getAttribute("movie");
        if (currentUser != null) {
            MovieReview movieReview = new MovieReview();
            movieReview.setComment(review_text);
            movieReview.setMovieID(currentMovie.getMovieID());
            movieReview.setUser(currentUser);
            movieReviewService.saveMovieReview(movieReview);
        } else {
            System.out.println("No login");
        }

        return "redirect:/movie/" + currentMovie.getMovieID();
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
