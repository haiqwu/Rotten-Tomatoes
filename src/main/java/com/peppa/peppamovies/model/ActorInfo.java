package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ActorInfo")
public class ActorInfo {
    @Id
    @GeneratedValue
    private int actorID;
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date dayOfBir;
    private String personalInfo;
    @ManyToMany(mappedBy = "movieActors")
    private List<MovieInfo> relatedMovies = new ArrayList<>();

    public ActorInfo() {
    }

    public int getActorID() {
        return actorID;
    }

    public void setActorID(int actorID) {
        this.actorID = actorID;
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

    public Date getDayOfBir() {
        return dayOfBir;
    }

    public void setDayOfBir(Date dayOfBir) {
        this.dayOfBir = dayOfBir;
    }

    public String getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(String personalInfo) {
        this.personalInfo = personalInfo;
    }

    public List<MovieInfo> getRelatedMovies() {
        return relatedMovies;
    }

    public void setRelatedMovies(List<MovieInfo> relatedMovies) {
        this.relatedMovies = relatedMovies;
    }

    @Override
    public String toString() {
        return "ActorInfo{" +
                "actorID=" + actorID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dayOfBir=" + dayOfBir +
                ", personalInfo='" + personalInfo + '\'' +
                ", relatedMovies=" + relatedMovies +
                '}';
    }
}
