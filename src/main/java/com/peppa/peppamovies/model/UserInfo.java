package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "UserInfo")
public class UserInfo {
    @Id
    @GeneratedValue
    private int userID;
    private String firstName;
    private String lastName;
    private String Email;
    private Byte[] password;
    private boolean isCritic;
    private boolean isEmailVerified;
    private String photo;
    @Temporal(TemporalType.DATE)
    private Date dateBecomingCritic;
    @Temporal(TemporalType.DATE)
    private Date dateDeActivatedFromCritic;
    private String followedSocialMedias;

    @ManyToMany(mappedBy = "interestedUsers")
    private List<MovieInfo> wantsToSeeList = new ArrayList<>();
    @OneToMany(mappedBy = "movie")
    private List<MovieReview> movieReviews = new ArrayList<>();
    @ManyToOne
    private CriticGroup group;
    @OneToOne(mappedBy = "user")
    private BusinessProposal bp;
    @OneToOne(mappedBy = "user")
    private Newsletter newsL;

    public UserInfo() {
    }

    public boolean checkName(){return true;}
    public boolean checkEmailFormat(){return true;}
    public boolean checkPassword(){return true;}
    public Byte[] hashPassword(String password){Byte[] passW = new Byte[5]; return passW;}
    public boolean verifyEmail(String email){return true;}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
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

    public Byte[] getPassword() {
        return password;
    }

    public void setPassword(Byte[] password) {
        this.password = password;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Email='" + Email + '\'' +
                ", password=" + Arrays.toString(password) +
                ", isCritic=" + isCritic +
                ", isEmailVerified=" + isEmailVerified +
                ", photo=" + photo +
                ", dateBecomingCritic=" + dateBecomingCritic +
                ", dateDeActivatedFromCritic=" + dateDeActivatedFromCritic +
                //", wantsToSeeList=" + wantsToSeeList +
                ", movieReviewsvies=" + movieReviews +
                ", followedSocialMedias=" + followedSocialMedias +
                ", group=" + group +
                '}';
    }
}
