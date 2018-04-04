package com.peppa.peppamovies.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CriticGroup")
public class CriticGroup {
    @Id
    @GeneratedValue
    private Long groupID;
    private String groupName;
    private String groupIntro;
    @OneToMany(mappedBy = "group")
    private List<UserInfo> groupParticipents = new ArrayList<>();

    public CriticGroup() {
    }

    public Long getGroupID() {
        return groupID;
    }

    public void setGroupID(Long groupID) {
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

    public void setGroupParticipents(List<UserInfo> groupParticipents) {
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
