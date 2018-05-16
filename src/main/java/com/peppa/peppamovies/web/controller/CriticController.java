package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.MovieInfo;
import com.peppa.peppamovies.model.MovieReview;
import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.MovieReviewService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CriticController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieReviewService movieReviewService;
    @Autowired
    private UserService userService;

    @GetMapping("/pt_critics")
    public String handlePTCritics(@PageableDefault(size = 8) Pageable pageable, Model model){
        List<List<Object>> latest_reviews = new ArrayList<>();
        Page<MovieReview> latest_review = movieReviewService.listReviewsByTime( pageable);
        List<MovieReview> latest_r = latest_review.getContent();
        for(MovieReview mr :latest_r )
        {
            List<Object> obj = new ArrayList<>();
            obj.add(mr);
            obj.add(  movieService.getMovie(mr.getMovieID())   );
            latest_reviews.add(obj);
        }
        Page<UserInfo> top_critics = userService.listCriticsByReview( pageable);
        model.addAttribute("top_critics",top_critics);
        model.addAttribute("latest_reviews",latest_reviews);
        return "critics";
    }

    @GetMapping("/apply_ct")
    public String handlePTBecome(HttpSession session){
        UserInfo user = (UserInfo) session.getAttribute("user");
        int size=user.getNumFollowers();
        boolean isCritic = user.isCritic();
        boolean isapply = user.isApplying_critic();

        if(isCritic == true){
            session.setAttribute("status",1);
        }
        else{
            if(isapply == true){
                session.setAttribute("status",2);
            }

            else{
                if (size<1){
                session.setAttribute("status",3);
                }
            }


        }



        return "apply_critics";
    }




    @GetMapping("/apply_critic_/{id}")
    public String handle_Apply_Critic_(@PathVariable Long id, HttpSession session,HttpServletRequest request)
    {
        String referer = request.getHeader("Referer");
        System.out.println(id);
        UserInfo user = userService.getUser(id);
        user.setApplying_critic(true);
        userService.updateUser(id, user);
        session.setAttribute("user",user);

//        return "index" ;
        return "redirect:" + referer;


    }


}
