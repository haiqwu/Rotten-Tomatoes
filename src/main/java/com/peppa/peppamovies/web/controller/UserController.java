package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginPage(){
        return "index";
    }
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password,
                              HttpSession session, RedirectAttributes attributes){
        UserInfo user = userService.checkUser(username, password);
        if(user != null){
            user.setPassW(null);
            System.out.println("Login Check: " +session);
            session.setAttribute("user", user);
            System.out.println("Login");
            return "index";
        }else{
            attributes.addFlashAttribute("message", "usename and password not match");
            System.out.println("NOLogin");
            return "redirect:index";
        }
    }
    public void handleLogout(){}
    public void handleRegisterButton(){}
    public void handleSucMesg(){}
    public void handleFailMesg(){}
    public void handleJoinNewsletter(){}
    public void handleFollowSocialMedia(){}
    public void handleChangePassword(){}
    public void handleUserHelpService(){}
    public void handleBusinessProposal(){}
    public void handleShowLicenseInfo(){}
    public void handleOpenPersonalInfo(){}
    public void handleShowCriticInfo(){}
    public void handleAboutPeppaTom(){}
    public void handleTomatometerRatingRule(){}
    public void handleCriticSearchBarFocus(){}
    public void handleCriticSearchBarTextChanging(){}
    public void handleCriticSearch(){}
    public void handleRTCriticHomeButton(){}
    public void handleCriticListButton(){}
    public void handleViewCriticGroups(){}
    public void handleViewLatestReviews(){}
    public void handleViewCriticsCriteria(){}
}
