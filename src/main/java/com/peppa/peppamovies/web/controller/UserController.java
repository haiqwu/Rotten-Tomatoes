package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.MovieRankingData;
import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.EmailService;
import com.peppa.peppamovies.service.MovieReviewService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import com.peppa.peppamovies.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MovieReviewService movieReviewService;
    private MovieRankingData movieRankingData;
    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String loginPage(@PageableDefault(size = 8, sort = {"releasedDate"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, Calendar.JANUARY, 1);
        Date date = cal.getTime();
        Page<MovieInfo> moviesOpen = movieService.listOpeningMovie(date, pageable);
        model.addAttribute("pageOpen", moviesOpen);
        Page<MovieInfo> moviesTop = movieService.listTopMovie(pageable);
        model.addAttribute("pageTop", moviesTop);
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
            final String uuid = UUID.randomUUID().toString();
            user.setRegisterUUID(uuid);
            try {
                emailService.sendRegisterVerification(user, uuid);
            } catch (MailException me) {
                me.printStackTrace();
            }
            userService.saveUser(user);
        } else { // already exist
            session.setAttribute("exist", true);
        }
        return "redirect:" + referer;
    }

    @GetMapping("/account_verification/{user_name}/{uu_id}")
    public String handleAccountVerification(@PathVariable String uu_id, @PathVariable String user_name) {
        UserInfo user = userService.getUserByUserName(user_name);
        if (user.isEmailVerified()) {
            System.out.println("Already verified");
            return "email_verify_repeat";
        }
        String uuid = user.getRegisterUUID();
        if (uuid == null || !uuid.equals(uu_id)) {
            System.out.println("Verify Failed");
            return "email_verify_fail";//time expire or verify key not exist
        }
        System.out.println("Verify Success");
        user.setEmailVerified(true);
        user.setRegisterUUID(null);
        userService.updateUser(user.getUserID(), user);
        return "email_verify_succ";
    }

    @GetMapping("/movie/{id}/wants_to_see")
    public String handleWantsToSee(HttpSession session) {
        UserInfo currentUser = (UserInfo) session.getAttribute("user");
        MovieInfo currentMovie = (MovieInfo) session.getAttribute("movie");
        if (currentUser != null) {
            boolean isDupli = false;
            List<MovieInfo> wts = currentUser.getWantsToSeeList();
            for (int i = 0; i < wts.size(); i++) {
                if (wts.get(i).getMovieID().equals(currentMovie.getMovieID())) {
                    isDupli = true;
                    break;
                }
            }
            if (!isDupli) {
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
            for (int i = 0; i < wts.size(); i++) {
                if (wts.get(i).getMovieID().equals(currentMovie.getMovieID())) {
                    isDupli = true;
                    break;
                }
            }
            if (!isDupli) {
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
    public ArrayList<List<MovieInfo>> loadMovieLists() {
        movieRankingData = new MovieRankingData();
        ArrayList<long[]> ranks = movieRankingData.loadMovieRankingData();
        int num_per_list = 8;

        //2. get comming_soon movies:
        long[] comming_soon_list = ranks.get(1);
        for (int i = 0; i < num_per_list; i++) {
            MovieInfo movie = movieService.getMovie(comming_soon_list[i]);
            movieRankingData.getComingSoonMovies().add(movie);
        }
        //4. certified_fresh_movies movies:
        long[] certified_fresh_movies_list = ranks.get(3);
        for (int i = 0; i < num_per_list; i++) {
            MovieInfo movie = movieService.getMovie(certified_fresh_movies_list[i]);
            movieRankingData.getCertifiedFreshMovies().add(movie);
        }
        ArrayList<List<MovieInfo>> loaded_movie_lists = new ArrayList<>();
        loaded_movie_lists.add(movieRankingData.getMoviesOpeningThisWeek());
        loaded_movie_lists.add(movieRankingData.getTopBoxMovies());
        loaded_movie_lists.add(movieRankingData.getCertifiedFreshMovies());
        loaded_movie_lists.add(movieRankingData.getComingSoonMovies());
        return loaded_movie_lists;
    }
//
//    public Byte[] toByteArr(byte[] bytes) {
//        Byte[] byteObjects = new Byte[bytes.length];
//        int i = 0;
//        for (byte b : bytes)
//            byteObjects[i++] = b;
//        return byteObjects;
//    }
}
