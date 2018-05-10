package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.EmailService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import com.peppa.peppamovies.util.MD5Util;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class PasswordController {
    @Autowired
    private UserService userService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private EmailService emailService;


    @GetMapping("/my_profile/{id}/delete_account")
    public String delAccount(@PathVariable Long id, HttpSession session){
        //UserInfo user = userService.getUser(id);

        //1. log out usr:
        session.removeAttribute("user");
        session.removeAttribute("found");
        // delete user:
        userService.deleteUser( id );

        return "redirect:/";
    }



    @GetMapping("/my_profile/{id}/changepassword")
    public String changePassword(@PathVariable Long id,@RequestParam String enteredPasswd, @RequestParam String newPasswd){
        UserInfo user = userService.getUser(id);
        String usrName  = user.getUserName();

        UserInfo user2 = userService.checkUser(usrName, MD5Util.mdCode(enteredPasswd));
        if (user2 != null) {
            //password correct:
            //set user's passwd to newPasswd
            String hashedPassword = MD5Util.mdCode(newPasswd);
            user2.setPassW(hashedPassword);
            userService.updateUser(user2.getUserID(), user2);
            return "redirect:/my_profile/"+ id;
        }
        else{
            //password wrong:
            System.out.println("Password verify failed.");
            return "redirect:/my_profile/"+ id +"/changepassword";
        }
    }


    @GetMapping("/my_profile/{id}/change_email")
    public String changeEmail(@PathVariable Long id,@RequestParam String enteredPasswd, @RequestParam String newEmail){
        UserInfo user = userService.getUser(id);
        String usrName  = user.getUserName();

        UserInfo user2 = userService.checkUser(usrName, MD5Util.mdCode(enteredPasswd));
        if (user2 != null) {
            //password correct:
            // send verification email
            final String uuid = UUID.randomUUID().toString();
            user2.setRegisterUUID(uuid);
            try {
                emailService.sendRegisterVerification(user2, uuid);
            } catch (MailException me) {
                me.printStackTrace();
            }
            user2.setEmail(   newEmail    );
            user2.setEmailVerified(false);
            userService.updateUser(user2.getUserID(), user2);
            return "redirect:/my_profile/"+ id ;
        }
        else{
            //password wrong:
            System.out.println("Password verify failed.");
            return "redirect:/my_profile/"+ id +"/change_email";
        }
    }









}
