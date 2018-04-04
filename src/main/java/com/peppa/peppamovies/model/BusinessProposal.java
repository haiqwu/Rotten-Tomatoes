package com.peppa.peppamovies.model;

import javax.persistence.*;
@Entity
@Table(name = "BusinessProposal")
public class BusinessProposal {
    @Id
    @GeneratedValue
    private Long BPID;
    private String firstname;
    private String lastname;
    private String companyname;
    private String companyLocation;
    private String position;
    private String website;
    private String email;
    private String phoneNum;
    private boolean isEmailVerified;
    private String contentPlan;
    @OneToOne
    private UserInfo user;

    public BusinessProposal() {
    }

    public boolean checkName(){return true;}
    public boolean checkEmailFormat(){return true;}
    public boolean checkPhoneNumFormat(){return true;}
    public boolean checkContentsPlan(){return true;}
    public boolean verifyEmail(String email){return true;}

    public Long getBPID() {
        return BPID;
    }

    public void setBPID(Long BPID) {
        this.BPID = BPID;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public String getContentPlan() {
        return contentPlan;
    }

    public void setContentPlan(String contentPlan) {
        this.contentPlan = contentPlan;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BusinessProposal{" +
                "BPID=" + BPID +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", companyname='" + companyname + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                ", position='" + position + '\'' +
                ", website='" + website + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", isEmailVerified=" + isEmailVerified +
                ", contentPlan=" + contentPlan +
                '}';
    }
}
