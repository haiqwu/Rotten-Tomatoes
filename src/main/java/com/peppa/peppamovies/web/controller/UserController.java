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
        byte[] temp =password.getBytes();
        Byte[] passwdByte = toByteArr(temp);

        UserInfo user = userService.checkUser(username, passwdByte);
        if(user != null){
            user.setPassW(null);
            session.setAttribute("user", user);
            System.out.println("LoginSuccess");
            return "index";
        }else{
            attributes.addFlashAttribute("message", "usename and password not match");
            System.out.println("NOLogin");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";

    }

    @PostMapping("/signup")
    public String handleSignUp(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String username,
                             @RequestParam String email, @RequestParam String password, @RequestParam String re_password,
                             HttpSession session, RedirectAttributes attributes){
        UserInfo user = new UserInfo();
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUserName(username);
        user.setEmail(email);
        if(password.equals(re_password)){
            byte[] temp =password.getBytes();
            Byte[] passwdByte = toByteArr(temp);
            user.setPassW(passwdByte);
        }else{}
        userService.saveUser(user);
        return "redirect:/";
    }

    public void handleSucMesg(){}
    public void handleFailMesg(){}
    public void handleJoinNewsletter(){}
    public void handleFollowSocialMedia(){}
    public void handleChangePassword(){}
    public void handleUserHelpService(){}
    public void handleBusinessProposal(){}
    @GetMapping("/licensing")
    public String handleShowLicenseInfo(){
        return "licensing";
    }
    public void handleOpenPersonalInfo(){}
    public void handleShowCriticInfo(){}
    @GetMapping("/about")
    public String handleAboutPeppaTom(){
        return "about";
    }
    public void handleTomatometerRatingRule(){}
    public void handleCriticSearchBarFocus(){}
    public void handleCriticSearchBarTextChanging(){}
    public void handleCriticSearch(){}
    public void handleRTCriticHomeButton(){}
    public void handleCriticListButton(){}
    public void handleViewCriticGroups(){}
    public void handleViewLatestReviews(){}
    public void handleViewCriticsCriteria(){}
    public Byte[] toByteArr(byte[] bytes){
        Byte[] byteObjects = new Byte[bytes.length];
        int i = 0;
        for(byte b: bytes)
            byteObjects[i++] = b;
        return byteObjects;
    }
}
