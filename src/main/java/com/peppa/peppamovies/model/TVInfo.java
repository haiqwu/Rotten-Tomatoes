package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "TVInfo")
public class TVInfo {
    @Id
    @GeneratedValue
    private Long tvID;
    private String secondaryID;
    private int season;
    private String titleType;
    private int runtimeMinutes;
    private String genres;
    private String tvName;
    @Temporal(TemporalType.DATE)
    private Date releasedDate;
    private String briefIntro;
    private double audianceRate;
    private double criticRate;
    private double totalRate;
    private String tvPoster;
    private String tvImages;
    private int criticRateCount;
    private int audiRateCount;
    @ManyToMany
    private List<ActorInfo> tvActors = new ArrayList<>();
    @ManyToMany(mappedBy = "wantsToSeeListTV")
    private List<UserInfo> interestedUsers = new ArrayList<>();
    @ManyToMany(mappedBy = "notInterestedListTV")
    private List<UserInfo> notInterestedUsers = new ArrayList<>();

    public TVInfo() {

    }

    public Long getTvID() {
        return tvID;
    }

    public void setTvID(Long tvID) {
        this.tvID = tvID;
    }

    public String getSecondaryID() {
        return secondaryID;
    }

    public void setSecondaryID(String secondaryID) {
        this.secondaryID = secondaryID;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setRuntimeMinutes(int runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
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

    public String getTvPoster() {
        return tvPoster;
    }

    public void setTvPoster(String tvPoster) {
        this.tvPoster = tvPoster;
    }

    public String getTvImages() {
        return tvImages;
    }

    public void setTvImages(String tvImages) {
        this.tvImages = tvImages;
    }

    public List<ActorInfo> getTvActors() {
        return tvActors;
    }

    public void setTvActors(List<ActorInfo> tvActors) {
        this.tvActors = tvActors;
    }

    public List<UserInfo> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(List<UserInfo> interestedUsers) {
        this.interestedUsers = interestedUsers;
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

    @Override
    public String toString() {
        return "TVInfo{" +
                "tvID=" + tvID +
                ", secondaryID='" + secondaryID + '\'' +
                ", season=" + season +
                ", titleType='" + titleType + '\'' +
                ", runtimeMinutes=" + runtimeMinutes +
                ", genres='" + genres + '\'' +
                ", tvName='" + tvName + '\'' +
                ", releasedDate=" + releasedDate +
                ", briefIntro='" + briefIntro + '\'' +
                ", audianceRate=" + audianceRate +
                ", criticRate=" + criticRate +
                ", totalRate=" + totalRate +
                ", tvPoster='" + tvPoster + '\'' +
                ", tvImages='" + tvImages + '\'' +
                ", criticRateCount=" + criticRateCount +
                ", audiRateCount=" + audiRateCount +
                ", tvActors=" + tvActors +
                ", interestedUsers=" + interestedUsers +
                ", notInterestedUsers=" + notInterestedUsers +
                '}';
    }
}

