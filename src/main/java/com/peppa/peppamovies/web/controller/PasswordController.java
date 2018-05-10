package com.peppa.peppamovies.web.controller;

import com.peppa.peppamovies.model.UserInfo;
import com.peppa.peppamovies.service.EmailService;
import com.peppa.peppamovies.service.MovieService;
import com.peppa.peppamovies.service.UserService;
import com.peppa.peppamovies.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @PostMapping("/my_profile/{id}/delete_account")
    public String delAccount(@PathVariable Long id, HttpSession session){
        //UserInfo user = userService.getUser(id);
        //1. log out usr:
        session.removeAttribute("user");
        session.removeAttribute("found");
        // delete user:
        userService.deleteUser( id );
        return "redirect:/";
    }



    @PostMapping("/my_profile/{id}/changepassword")
    public String changePassword(@PathVariable Long id,@RequestParam String old_password, @RequestParam String new_password){
        UserInfo user  = userService.getUser(id);
        String usrName = user.getUserName();

        UserInfo user2 = userService.checkUser(usrName, MD5Util.mdCode(old_password));
        if (user2 != null) {
            //password correct:
            //set user's passwd to newPasswd
            String hashedPassword = MD5Util.mdCode(new_password);
            user2.setPassW(hashedPassword);
            userService.updateUser(user2.getUserID(), user2);
            return "redirect:/my_profile/"+ id;
        }
        else{
            //password wrong:
            System.out.println("Password verify failed.");
            return "redirect:"+"/change_password/"+ id ;
        }
    }


    @PostMapping("/my_profile/{id}/change_email")
    public String changeEmail(@PathVariable Long id,@RequestParam String enteredPasswd, @RequestParam String email_change_email){
        UserInfo user = userService.getUser(id);
        String usrName  = user.getUserName();

        UserInfo user2 = userService.checkUser(usrName, MD5Util.mdCode(enteredPasswd));
        if (user2 != null) {
            //password correct:
            // send verification email
            final String uuid = UUID.randomUUID().toString();
            user2.setRegisterUUID(uuid);

            user2.setEmail(   email_change_email    );
            try {
                emailService.sendRegisterVerification(user2, uuid);
            } catch (MailException me) {
                me.printStackTrace();
            }
            user2.setEmailVerified(false);
            userService.updateUser(user2.getUserID(), user2);
            System.out.println("changed email.");
            return "redirect:/my_profile/"+ id;
        }
        else{
            //password wrong:
            System.out.println("Password verify failed.");
            return "redirect:"+"/change_email/"+ id ;
        }
    }


    @GetMapping("/forgot_password_username_prompt")
    public String toForgotPasswordUserNamePrompt()
    {
        return "forgot_password_username_prompt_page";
    }


    @PostMapping("/forgot_password")
    public String forgotPassword(@RequestParam String user_name)
    {
        UserInfo usr = userService.getUserByUserName(user_name);

        if( usr!=null){
            final String uuid = UUID.randomUUID().toString();
            usr.setResetPasswordUUID(uuid);
            try {
                emailService.sendResetPasswordEmail(usr, uuid);
            } catch (MailException me) {
                me.printStackTrace();
            }
            userService.updateUser( usr.getUserID(),usr  );
            System.out.println("Request is sent to your email.");
            return "redirect:/";//plz check your email
        }
        else
        {
            //usr name not found:
            System.out.println("User name is not found.");
            return "redirect:/forgot_password_username_prompt";
        }

    }


    @GetMapping("/reset_password/{user_name}/{uu_id}")
    public String resetPasswordLinkRequest(@PathVariable String uu_id, @PathVariable String user_name,HttpSession session) {
        UserInfo user = userService.getUserByUserName(user_name);
        String uuid = user.getResetPasswordUUID();
        if (uuid == null || !uuid.equals(uu_id)) {
            System.out.println("Reset Failed, uuid null or wrong");
            return "email_verify_fail";//time expire or reset key not exist
        }
        System.out.println("Verify Success");
        user.setEmailVerified(true);
        user.setResetPasswordUUID(null);//make sure it is only can be used once
        userService.updateUser(user.getUserID(), user);
        session.setAttribute("fp_usr", user);
        return "change_password_attemptfor_forgot_password";
    }

    @PostMapping("/true_change_password_fp")
    public String true_change_password_fp_request(@RequestParam String new_password, HttpSession session)
    {
        UserInfo user2 = (UserInfo) session.getAttribute("fp_usr");

        //set user's passwd to newPasswd
        String hashedPassword = MD5Util.mdCode(new_password);
        user2.setPassW(hashedPassword);
        userService.updateUser(user2.getUserID(), user2);
        return "redirect:/";
    }








}
