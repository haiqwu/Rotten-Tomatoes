package com.peppa.peppamovies.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "UserInfo")
public class UserInfo {
    @Id
    @GeneratedValue
    private Long userID;
    private String userName;
    private String registerUUID;
    private String resetPasswordUUID;
    private String firstName;
    private String lastName;
    private String Email;
    private String passW;
    private boolean isCritic;

    private boolean officially_blocked;
    private boolean reported;
    private boolean applying_critic;

    private boolean isEmailVerified;
    private String photo;
    @Temporal(TemporalType.DATE)
    private Date dateBecomingCritic;
    @Temporal(TemporalType.DATE)
    private Date dateDeActivatedFromCritic;
    private String followedSocialMedias;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<MovieInfo> wantsToSeeList = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<MovieInfo> notInterestedList = new ArrayList<>();
    @OneToMany(mappedBy = "reviewUser" )
    private List<MovieReview> movieReviews = new ArrayList<>();
    @ManyToOne
    private CriticGroup group;
    @OneToOne(mappedBy = "user")
    private BusinessProposal bp;
    @OneToOne(mappedBy = "user")
    private Newsletter newsL;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<TVInfo> wantsToSeeListTV = new ArrayList<>();
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<TVInfo> notInterestedListTV = new ArrayList<>();

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="follow_relation",
            joinColumns={@JoinColumn(name="userid")},
            inverseJoinColumns={@JoinColumn(name="followingid")})
    private Set<UserInfo> followings = new HashSet<UserInfo>();

    @ManyToMany(mappedBy="followings")
    private Set<UserInfo> followers = new HashSet<UserInfo>();
    private int numFollowers;
    private boolean shy;

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

    public String getRegisterUUID() {
        return registerUUID;
    }

    public void setRegisterUUID(String registerUUID) {
        this.registerUUID = registerUUID;
    }

    public String getResetPasswordUUID() {
        return resetPasswordUUID;
    }

    public void setResetPasswordUUID(String resetPasswordUUID) {
        this.resetPasswordUUID = resetPasswordUUID;
    }

    public List<MovieInfo> getNotInterestedList() {
        return notInterestedList;
    }

    public void setNotInterestedList(List<MovieInfo> notInterestedList) {
        this.notInterestedList = notInterestedList;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isApplying_critic() {
        return applying_critic;
    }

    public void setApplying_critic(boolean applying_critic) {
        this.applying_critic = applying_critic;
    }

    public boolean isOfficially_blocked() {
        return officially_blocked;
    }

    public void setOfficially_blocked(boolean officially_blocked) {
        this.officially_blocked = officially_blocked;
    }

    public List<TVInfo> getWantsToSeeListTV() {
        return wantsToSeeListTV;
    }

    public void setWantsToSeeListTV(List<TVInfo> wantsToSeeListTV) {
        this.wantsToSeeListTV = wantsToSeeListTV;
    }

    public List<TVInfo> getNotInterestedListTV() {
        return notInterestedListTV;
    }

    public void setNotInterestedListTV(List<TVInfo> notInterestedListTV) {
        this.notInterestedListTV = notInterestedListTV;
    }

    public Pattern getVALID_EMAIL_ADDRESS_REGEX() {
        return VALID_EMAIL_ADDRESS_REGEX;
    }

    public final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public Set<UserInfo> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserInfo> followers) {
        this.followers = followers;
    }

    public Set<UserInfo> getFollowings() {
        return followings;
    }

    public void setFollowings(Set<UserInfo> followings) {
        this.followings = followings;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public boolean isShy() {
        return shy;
    }

    public void setShy(boolean shy) {
        this.shy = shy;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", registerUUID='" + registerUUID + '\'' +
                ", resetPasswordUUID='" + resetPasswordUUID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", Email='" + Email + '\'' +
                ", passW='" + passW + '\'' +
                ", isCritic=" + isCritic +
                ", officially_blocked=" + officially_blocked +
                ", reported=" + reported +
                ", applying_critic=" + applying_critic +
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
                ", wantsToSeeListTV=" + wantsToSeeListTV +
                ", notInterestedListTV=" + notInterestedListTV +
                ", followings=" + followings +
                ", followers=" + followers +
                ", numFollowers=" + numFollowers +
                ", shy=" + shy +
                ", VALID_EMAIL_ADDRESS_REGEX=" + VALID_EMAIL_ADDRESS_REGEX +
                '}';
    }
}
