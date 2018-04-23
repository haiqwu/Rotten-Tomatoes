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
}
