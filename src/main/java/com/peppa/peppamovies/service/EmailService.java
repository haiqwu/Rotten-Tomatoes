package com.peppa.peppamovies.service;

import com.peppa.peppamovies.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendRegisterVerification(UserInfo userInfo, String uuid) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userInfo.getEmail());
        mail.setFrom("5297haiqi@gmail.com");
        mail.setSubject("Welcome to Peppa");
        mail.setText("Hi, " + userInfo.getFirstName() + " "
                + userInfo.getLastName() + ":\n" +
                " Welcome to Peppa, Please click the link below to verify your email address.\n\t" +
                "http://localhost:8080/account_verification/" + userInfo.getUserName() + "/" + uuid
        );
        javaMailSender.send(mail);

    }

    public void sendResetPasswordEmail(UserInfo userInfo, String uuid) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userInfo.getEmail());
        mail.setFrom("5297haiqi@gmail.com");
        mail.setSubject("Password Reset Request");
        mail.setText("Hi, " + userInfo.getFirstName() + " "
                + userInfo.getLastName() + ":\n" +
                " You are requesting to reset password, Please click the link below to reset your password.\n\t"
                +  "*******Note: if you are not attempting to change the password, simply IGNORE this email. "
                +
                "http://localhost:8080/reset_password/" + userInfo.getUserName() + "/" + uuid
        );
        javaMailSender.send(mail);

    }


    public void sendRejectCriticApplicationEmail(UserInfo userInfo ) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userInfo.getEmail());
        mail.setFrom("5297haiqi@gmail.com");
        mail.setSubject("Peppa - Critic Application Result");
        mail.setText("Hi, " + userInfo.getFirstName() + " "
                + userInfo.getLastName() + ":\n" +
                "\tThanks for your application to become a critic.\n\t" +
                "Based on our record, you may not be qualified for a critic in Peppa Movie yet. " +
                "You may still want to apply for it again. If you have any question, please feel free" +
                "to contact us.\n\n\n\t" +
                "Peppa Team"

        );
        javaMailSender.send(mail);

    }


}
