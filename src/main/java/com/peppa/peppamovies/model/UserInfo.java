package com.peppa.peppamovies.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "UserInfo")
public class UserInfo {
    @Id
    @GeneratedValue
    private Long userID;
    private String userName;

    @Basic(fetch = FetchType.EAGER)
    @Lob
    private String firstName;
    private String lastName;
    private String Email;
    private String passW;
    private boolean isCritic;
    private boolean isEmailVerified;
    private String photo;
    @Temporal(TemporalType.DATE)
    private Date dateBecomingCritic;
    @Temporal(TemporalType.DATE)
    private Date dateDeActivatedFromCritic;
    private String followedSocialMedias;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @Fetch(org.hibernate.annotations.FetchMode.JOIN)
    private List<MovieInfo> wantsToSeeList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MovieInfo> notInterestedList = new ArrayList<>();


    @OneToMany(mappedBy = "reviewUser")
    private List<MovieReview> movieReviews = new ArrayList<>();
    @ManyToOne
    private CriticGroup group;
    @OneToOne(mappedBy = "user")
    private BusinessProposal bp;
    @OneToOne(mappedBy = "user")
    private Newsletter newsL;

    public UserInfo() {
    }

    public boolean checkName() {
        return true;
    }

    public boolean checkEmailFormat(String email) {
        return validateEmail(email);
    }

    public boolean checkPassword() {
        return true;
    }

    public String getPassW() {
        return passW;
    }

    public void setPassW(String passW) {
        this.passW = passW;
    }

    public boolean verifyEmail(String email) {
        return true;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isCritic() {
        return isCritic;
    }

    public void setCritic(boolean critic) {
        isCritic = critic;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getDateBecomingCritic() {
        return dateBecomingCritic;
    }

    public void setDateBecomingCritic(Date dateBecomingCritic) {
        this.dateBecomingCritic = dateBecomingCritic;
    }

    public Date getDateDeActivatedFromCritic() {
        return dateDeActivatedFromCritic;
    }

    public void setDateDeActivatedFromCritic(Date dateDeActivatedFromCritic) {
        this.dateDeActivatedFromCritic = dateDeActivatedFromCritic;
    }

    public CriticGroup getGroup() {
        return group;
    }

    public void setGroup(CriticGroup group) {
        this.group = group;
    }

    public BusinessProposal getBp() {
        return bp;
    }

    public void setBp(BusinessProposal bp) {
        this.bp = bp;
    }

    public String getFollowedSocialMedias() {
        return followedSocialMedias;
    }

    public void setFollowedSocialMedias(String followedSocialMedias) {
        this.followedSocialMedias = followedSocialMedias;
    }

    public Newsletter getNewsL() {
        return newsL;
    }

    public void setNewsL(Newsletter newsL) {
        this.newsL = newsL;
    }

    public List<MovieInfo> getWantsToSeeList() {
        return wantsToSeeList;
    }

    public void setWantsToSeeList(List<MovieInfo> wantsToSeeList) {
        this.wantsToSeeList = wantsToSeeList;
    }

    public List<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(List<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public List<MovieInfo> getNotInterestedList() {
        return notInterestedList;
    }

    public void setNotInterestedList(List<MovieInfo> notInterestedList) {
        this.notInterestedList = notInterestedList;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Email='" + Email + '\'' +
                ", passW='" + passW + '\'' +
                ", isCritic=" + isCritic +
                ", isEmailVerified=" + isEmailVerified +
                ", photo='" + photo + '\'' +
                ", dateBecomingCritic=" + dateBecomingCritic +
                ", dateDeActivatedFromCritic=" + dateDeActivatedFromCritic +
                ", followedSocialMedias='" + followedSocialMedias + '\'' +
                ", wantsToSeeList=" + wantsToSeeList +
                ", notInterestedList=" + notInterestedList +
                ", movieReviews=" + movieReviews +
                ", group=" + group +
                ", bp=" + bp +
                ", newsL=" + newsL +
                ", VALID_EMAIL_ADDRESS_REGEX=" + VALID_EMAIL_ADDRESS_REGEX +
                '}';
    }

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
}
