package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CriticGroup")
public class CriticGroup {
    @Id
    @GeneratedValue
    private int groupID;
    private String groupName;
    private String groupIntro;
    @OneToMany(mappedBy = "group")
    private List<UserInfo> groupParticipents = new ArrayList<>();

    public CriticGroup() {
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntro() {
        return groupIntro;
    }

    public void setGroupIntro(String groupIntro) {
        this.groupIntro = groupIntro;
    }

    public List<UserInfo> getGroupParticipents() {
        return groupParticipents;
    }

    public void setGroupparticipents(List<UserInfo> groupParticipents) {
        this.groupParticipents = groupParticipents;
    }

    @Override
    public String toString() {
        return "CriticGroup{" +
                "groupID=" + groupID +
                ", groupName='" + groupName + '\'' +
                ", groupIntro='" + groupIntro + '\'' +
                ", groupParticipents=" + groupParticipents +
                '}';
    }
}
