package com.peppa.peppamovies.model;

import javax.persistence.*;

@Entity
@Table(name = "Newsletter")
public class Newsletter {
    @Id
    @GeneratedValue
    private Long newsletterID;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEmailVerified;
    @OneToOne
    private UserInfo user;

    public Newsletter() {
    }

    public boolean checkName(){return true;}
    public boolean checkEmailFormat(){return true;}
    public boolean verifyEmail(String email){return true;}

    public Long getNewsletterID() {
        return newsletterID;
    }

    public void setNewsletterID(Long newsletterID) {
        this.newsletterID = newsletterID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Newsletter{" +
                "newsletterID=" + newsletterID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isEmailVerified=" + isEmailVerified +
                '}';
    }
}
