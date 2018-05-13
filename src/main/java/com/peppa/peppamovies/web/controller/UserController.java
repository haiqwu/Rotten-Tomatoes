package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.*;
import com.peppa.peppamovies.service.*;
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
    @Autowired
    private TVService tvService;

    @GetMapping("/")
    public String loginPage(@PageableDefault(size = 8) Pageable pageable, Model model) {
        Date date = new Date();
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
        Page<MovieInfo> moviesOpen = movieService.listOpeningMovie(dateStart, dateEnd, pageable);
        model.addAttribute("pageOpen", moviesOpen);
        Page<MovieInfo> moviesTop = movieService.listTopMovie(pageable);
        model.addAttribute("pageTop", moviesTop);
        Page<MovieInfo> moviesComing = movieService.listComing(date, pageable);
        model.addAttribute("pageComing", moviesComing);
        Page<TVInfo> tvTop = tvService.listTopRatedTV(pageable);
        model.addAttribute("pageTVTop", tvTop);
        Page<TVInfo> tvCriticTop = tvService.listCriticTopRatedTV(pageable);
        model.addAttribute("pageCriticTVTop", tvCriticTop);
        return "index";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        UserInfo user = userService.checkUser(username, MD5Util.mdCode(password));
        session.setAttribute("type",0); // not login click
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("found", true);

            if(user.getUserName().equals("admin")){
                session.setAttribute("type",1); // admin
            }
            else{
                session.setAttribute("type",2); // common user
            }
            return "redirect:" + referer;
        } else {
            session.setAttribute("found", false);
            session.setAttribute("type",3); // not found
            System.out.println("NOLogin");
            return "redirect:" + referer;
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session, HttpServletRequest request) {
        //String referer = request.getHeader("Referer");
        session.removeAttribute("user");
        session.removeAttribute("found");
        session.removeAttribute("type");
        return "redirect:" ;
    }


    @GetMapping("/admin_summary")
    public String handleAdminSummary(){
        return "admin_summary";
    }



    @GetMapping("/my_profile/{id}")
    public String handleProfileSummaryPage(@PathVariable Long id, Model model, HttpSession session) {
        UserInfo user = userService.getUser(id);
        List<MovieReview> ratedMovies = user.getMovieReviews();

        List<List<Object>> movies = new ArrayList<>();

        List<MovieInfo> wantsToSeeList = user.getWantsToSeeList();
        List<MovieInfo> notInterested = user.getNotInterestedList();
        Set<UserInfo> followers = user.getFollowers();
        Set<UserInfo> followings = user.getFollowings();

        for(MovieReview mr: ratedMovies){
            List<Object> obj = new ArrayList<>();
            obj.add(mr);
            obj.add(movieService.getMovie(mr.getMovieID()));
            movies.add(obj);
        }
        model.addAttribute("rateMovies", movies);
        model.addAttribute("wantToSee", wantsToSeeList);
        model.addAttribute("notInterested", notInterested);
        model.addAttribute("followers", followers);
        model.addAttribute("followings", followings);
        model.addAttribute("currentUser", user);
        return "profile_template";
    }

    @GetMapping("/my_profile/delete/{mid}")
    public String handleDeleteReview(@PathVariable Long mid, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        List<MovieReview> ratedMovies = user.getMovieReviews();
        for(MovieReview mr: ratedMovies){
            if(mr.getMovieID().equals(mid)){
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
                ratedMovies.remove(mr);
                user.setMovieReviews(ratedMovies);
                movieReviewService.deleteReview(mr.getReviewID());
                userService.updateUser(user.getUserID(),user);
                session.removeAttribute("user");
                session.setAttribute("user", user);
                break;
            }
        }
        return "redirect:/my_profile/" + user.getUserID();
    }

    @GetMapping("/my_profile/follow/{id}")
    public String handleFollow(@PathVariable Long id, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        UserInfo currentUser = userService.getUser(id);
        currentUser.setNumFollowers(currentUser.getNumFollowers()+1);
        Set<UserInfo> followings = user.getFollowings();
        boolean isDulip = false;
        for(UserInfo ui: followings){
            if(ui.getUserID().equals(id)){
                isDulip=true;
                break;
            }
        }
        if(!isDulip){
            user.getFollowings().add(currentUser);
            userService.updateUser(user.getUserID(), user);
            session.setAttribute("user", user);
        }

        return "redirect:/my_profile/" + currentUser.getUserID();
    }

    @GetMapping("/my_profile/delete/wantsToSee/{mid}")
    public String handleDeleteWantsToSee(@PathVariable Long mid, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        List<MovieInfo> movies = user.getWantsToSeeList();
        for(MovieInfo mi: movies){
            if(mi.getMovieID().equals(mid)){
                movies.remove(mi);
                user.setWantsToSeeList(movies);
                userService.updateUser(user.getUserID(),user);
                session.setAttribute("user", user);
                break;
            }
        }
        return "redirect:/my_profile/" + user.getUserID();
    }

    @GetMapping("/my_profile/delete/notInterested/{mid}")
    public String handleDeleteNotInteresed(@PathVariable Long mid, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        List<MovieInfo> movies = user.getNotInterestedList();
        for(MovieInfo mi: movies){
            if(mi.getMovieID().equals(mid)){
                movies.remove(mi);
                user.setNotInterestedList(movies);
                userService.updateUser(user.getUserID(),user);
                session.setAttribute("user", user);
                break;
            }
        }
        return "redirect:/my_profile/" + user.getUserID();
    }

    @GetMapping("/my_profile/delete/followings/{id}")
    public String handleDeleteFollowings(@PathVariable Long id, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        UserInfo followingUser = userService.getUser(id);
        followingUser.setNumFollowers(followingUser.getNumFollowers()-1);
        Set<UserInfo> followings = user.getFollowings();
        for(UserInfo ui: followings){
            if(ui.getUserID().equals(id)){
                followings.remove(ui);
                user.setFollowings(followings);
                userService.updateUser(user.getUserID(),user);
                session.setAttribute("user", user);
                break;
            }
        }
        return "redirect:/my_profile/" + user.getUserID();
    }

    @GetMapping("/account/{id}")
    public String handleAccountSetting(@PathVariable Long id){
        System.out.println(id);
        return "account_set";
    }

    @GetMapping("/personal_info/{id}")
    public String handlePersonalInfo(@PathVariable Long id){
        System.out.println(id);
        return "personal_info";
    }

    @GetMapping("/change_email/{id}")
    public String handleChangeEmail(@PathVariable Long id){
        System.out.println(id);
        return "change_email";
    }

    @GetMapping("/change_password/{id}")
    public String handleChangePassword(@PathVariable Long id){
        System.out.println(id);
        return "change_password";
    }

    @GetMapping("/privacy_setting/{id}")
    public String handlePrivacySetting(@PathVariable Long id){
        System.out.println(id);
        return "privacy_setting";
    }

    @GetMapping("/delete_account/{id}")
    public String handleDeleteAccount(@PathVariable Long id){
        System.out.println(id);
        return "delete_account";
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
            List<MovieInfo> nit = currentUser.getNotInterestedList();
            for (int i = 0; i < wts.size(); i++) {
                if (wts.get(i).getMovieID().equals(currentMovie.getMovieID())) {
                    isDupli = true;
                    break;
                }
            }
            for (MovieInfo mi: nit){
                if(mi.getMovieID().equals(currentMovie.getMovieID())){
                    nit.remove(mi);
                    currentUser.setNotInterestedList(nit);
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
            List<MovieInfo> nit = currentUser.getNotInterestedList();
            List<MovieInfo> wts = currentUser.getWantsToSeeList();
            for (int i = 0; i < nit.size(); i++) {
                if (nit.get(i).getMovieID().equals(currentMovie.getMovieID())) {
                    isDupli = true;
                    break;
                }
            }
            for (MovieInfo mi: wts){
                if(mi.getMovieID().equals(currentMovie.getMovieID())){
                    currentUser.getWantsToSeeList().remove(mi);
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
    public String handlePost(@RequestParam String review_text, int star_rate, HttpSession session) {
        UserInfo currentUser = (UserInfo) session.getAttribute("user");
        MovieInfo currentMovie = (MovieInfo) session.getAttribute("movie");
        Date today = new Date();
        if (currentUser != null) {
            boolean isReviewed = false;
            List<MovieReview> movieReviews = currentUser.getMovieReviews();
            for(MovieReview mr: movieReviews){
                if(mr.getMovieID().equals(currentMovie.getMovieID())){
                    mr.setComment(review_text);
                    mr.setRate(star_rate*20);
                    mr.setDayCommented(today);
                    movieReviewService.updateMovieReview(mr.getReviewID(), mr);
                    currentUser.setMovieReviews(movieReviews);
                    session.setAttribute("user", currentUser);
                    isReviewed = true;
                    break;
                }
            }
            if(!isReviewed) {
                MovieReview movieReview = new MovieReview();
                movieReview.setComment(review_text);
                movieReview.setRate(star_rate*20);
                movieReview.setDayCommented(today);
                movieReview.setMovieID(currentMovie.getMovieID());
                movieReview.setUser(currentUser);
                movieReviewService.saveMovieReview(movieReview);
                movieReviews.add(movieReview);
                currentUser.setMovieReviews(movieReviews);
                session.setAttribute("user", currentUser);
                if(currentUser.isCritic()){
                    if(currentMovie.getCriticRateCount()==0){
                        currentMovie.setCriticRate(star_rate*20);
                        currentMovie.setCriticRateCount(currentMovie.getCriticRateCount()+1);
                    }else{
                        currentMovie.setCriticRate(currentMovie.getCriticRate()*currentMovie.getCriticRateCount());
                        currentMovie.setCriticRateCount(currentMovie.getCriticRateCount()+1);
                        currentMovie.setCriticRate((star_rate*20+currentMovie.getCriticRate())/currentMovie.getCriticRateCount());
                    }
                }else{
                    if(currentMovie.getAudiRateCount()==0){
                        currentMovie.setAudianceRate(star_rate*20);
                        currentMovie.setAudiRateCount(currentMovie.getAudiRateCount()+1);
                    }else{
                        currentMovie.setAudianceRate(currentMovie.getAudianceRate()*currentMovie.getAudiRateCount());
                        currentMovie.setAudiRateCount(currentMovie.getAudiRateCount()+1);
                        currentMovie.setAudianceRate((star_rate*20+currentMovie.getAudiRateCount())/currentMovie.getAudiRateCount());
                    }
                }
                currentMovie.setTotalRate((currentMovie.getCriticRate()+currentMovie.getAudianceRate())/2);
                movieService.updateMovie(currentMovie.getMovieID(), currentMovie);
            }
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

    @GetMapping("/email_verify")
    public String handleEmailVerify(){
        return "index";
    }





    @GetMapping("/help")
    public String handleHelp(){
        return "help";
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

//        //2. get comming_soon movies:
//        long[] comming_soon_list = ranks.get(1);
//        for (int i = 0; i < num_per_list; i++) {
//            MovieInfo movie = movieService.getMovie(comming_soon_list[i]);
//            movieRankingData.getComingSoonMovies().add(movie);
//        }
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
