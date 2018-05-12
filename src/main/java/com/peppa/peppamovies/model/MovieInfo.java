package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MovieInfo")
public class MovieInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long movieID;
    private String secondaryID;
    private String titleType;
    private int runtimeMinutes;
    private String genres;
    private String movieName;
    @Temporal(TemporalType.DATE)
    private Date releasedDate;
    private String briefIntro;
    private double audianceRate;
    private double criticRate;
    private double totalRate;
    private String moviePoster;
    private String movieImages;
    private String movieTrailers;
    private int criticRateCount;
    private int audiRateCount;
    private int box_office;
    @ManyToMany
    private List<ActorInfo> movieActors = new ArrayList<>();
    @ManyToMany(mappedBy = "wantsToSeeList")
    private List<UserInfo> interestedUsers = new ArrayList<>();
    @ManyToMany(mappedBy = "notInterestedList")
    private List<UserInfo> notInterestedUsers = new ArrayList<>();

    public MovieInfo() {
    }

    public Long getMovieID() {
        return movieID;
    }

    public String getSecondaryID() {
        return secondaryID;
    }

    public void setSecondaryID(String secondaryID) {
        this.secondaryID = secondaryID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
    }

    public double getAudianceRate() {
        return audianceRate;
    }

    public void setAudianceRate(double audianceRate) {
        this.audianceRate = audianceRate;
    }

    public double getCriticRate() {
        return criticRate;
    }

    public void setCriticRate(double criticRate) {
        this.criticRate = criticRate;
    }

    public double getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(double totalRate) {
        this.totalRate = totalRate;
    }

    public String getMoviePoster() {
        return this.moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieImages() {
        return movieImages;
    }

    public void setMovieImages(String movieImages) {
        this.movieImages = movieImages;
    }

    public String getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(String movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public List<ActorInfo> getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(List<ActorInfo> movieActors) {
        this.movieActors = movieActors;
    }

    public List<UserInfo> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(List<UserInfo> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public String getTitleType() {
        return titleType;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public void setRuntimeMinutes(int runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public List<UserInfo> getNotInterestedUsers() {
        return notInterestedUsers;
    }

    public void setNotInterestedUsers(List<UserInfo> notInterestedUsers) {
        this.notInterestedUsers = notInterestedUsers;
    }

    public int getCriticRateCount() {
        return criticRateCount;
    }

    public void setCriticRateCount(int criticRateCount) {
        this.criticRateCount = criticRateCount;
    }

    public int getAudiRateCount() {
        return audiRateCount;
    }

    public void setAudiRateCount(int audiRateCount) {
        this.audiRateCount = audiRateCount;
    }

    public int getBox_office() {
        return box_office;
    }

    public void setBox_office(int box_office) {
        this.box_office = box_office;
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "movieID=" + movieID +
                ", secondaryID='" + secondaryID + '\'' +
                ", titleType='" + titleType + '\'' +
                ", runtimeMinutes=" + runtimeMinutes +
                ", genres='" + genres + '\'' +
                ", movieName='" + movieName + '\'' +
                ", releasedDate=" + releasedDate +
                ", briefIntro='" + briefIntro + '\'' +
                ", audianceRate=" + audianceRate +
                ", criticRate=" + criticRate +
                ", totalRate=" + totalRate +
                ", moviePoster='" + moviePoster + '\'' +
                ", movieImages='" + movieImages + '\'' +
                ", movieTrailers='" + movieTrailers + '\'' +
                ", criticRateCount=" + criticRateCount +
                ", audiRateCount=" + audiRateCount +
                ", box_office=" + box_office +
                ", movieActors=" + movieActors +
                ", interestedUsers=" + interestedUsers +
                ", notInterestedUsers=" + notInterestedUsers +
                '}';
    }
}
