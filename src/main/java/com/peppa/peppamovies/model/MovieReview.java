package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MovieReview")
public class MovieReview implements Comparable<MovieReview>{
    @Id
    @GeneratedValue
    private Long reviewID;
    private Long movieID;

    private double rate;
    private String comment;
    @Temporal(TemporalType.DATE)
    private Date dayCommented;
    private String groupName;
    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private UserInfo reviewUser;

    private boolean is_critic_review;

    private boolean reported;

    public MovieReview() {
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public UserInfo getUser() {
        return reviewUser;
    }

    public void setUser(UserInfo user) {
        this.reviewUser = user;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDayCommented() {
        return dayCommented;
    }

    public void setDayCommented(Date dayCommented) {
        this.dayCommented = dayCommented;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public UserInfo getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(UserInfo reviewUser) {
        this.reviewUser = reviewUser;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isIs_critic_review() {
        return is_critic_review;
    }

    public void setIs_critic_review(boolean is_critic_review) {
        this.is_critic_review = is_critic_review;
    }

    @Override
    public String toString() {
        return "MovieReview{" +
                "reviewID=" + reviewID +
                ", movieID=" + movieID +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", dayCommented=" + dayCommented +
                ", groupName='" + groupName + '\'' +
                ", reviewUser=" + reviewUser +
                ", is_critic_review=" + is_critic_review +
                ", reported=" + reported +
                '}';
    }

    @Override
    public int compareTo(MovieReview that) {
        double rated1 = this.getRate();
        double rated2 = that.getRate();

        if (rated1 > rated2) {
            return 1;
        } else if (rated1 < rated2) {
            return -1;
        } else {
            return 0;
        }
    }
}
