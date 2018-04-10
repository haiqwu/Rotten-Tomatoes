package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MovieReview")
public class MovieReview {
    @Id
    @GeneratedValue
    private Long reviewID;
    private double rate;
    private String comment;
    @Temporal(TemporalType.DATE)
    private Date dayCommented;
    private String groupName;
    @ManyToOne
    private MovieInfo movie;

    public MovieReview() {
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public MovieInfo getMovie() {
        return movie;
    }

    public void setMovie(MovieInfo movie) {
        this.movie = movie;
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

    @Override
    public String toString() {
        return "MovieReview{" +
                "reviewID=" + reviewID +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", dayCommented=" + dayCommented +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
