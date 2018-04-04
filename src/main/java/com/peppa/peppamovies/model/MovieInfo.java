package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MovieInfo")
public class MovieInfo {
    @Id
    @GeneratedValue
    private Long movieID;
    private String secondaryID;
    private String movieName;
    @Temporal(TemporalType.DATE)
    private Date yearOpening;
    private String briefIntro;
    private double audianceRate;
    private double criticRate;
    private double totalRate;
    private String moviePoster;
    private String movieImages;
    private String movieTrailers;
    private int movieRank;
    @ManyToMany
    private List<ActorInfo> movieActors = new ArrayList<>();
    @ManyToMany
    private List<UserInfo> interestedUsers = new ArrayList<>();

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

    public Date getYearOpening() {
        return yearOpening;
    }

    public void setYearOpening(Date yearOpening) {
        this.yearOpening = yearOpening;
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
        return moviePoster;
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

    public int getMovieRank() {
        return movieRank;
    }

    public void setMovieRank(int movieRank) {
        this.movieRank = movieRank;
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

    @Override
    public String toString() {
        return "MovieInfo{" +
                "movieID=" + movieID +
                ", secondaryID='" + secondaryID + '\'' +
                ", movieName='" + movieName + '\'' +
                ", yearOpening=" + yearOpening +
                ", briefIntro='" + briefIntro + '\'' +
                ", audianceRate=" + audianceRate +
                ", criticRate=" + criticRate +
                ", totalRate=" + totalRate +
                ", moviePoster='" + moviePoster + '\'' +
                ", movieImages='" + movieImages + '\'' +
                ", movieTrailers='" + movieTrailers + '\'' +
                ", movieRank=" + movieRank +
                ", movieActors=" + movieActors +
                ", interestedUsers=" + interestedUsers +
                '}';
    }
}
