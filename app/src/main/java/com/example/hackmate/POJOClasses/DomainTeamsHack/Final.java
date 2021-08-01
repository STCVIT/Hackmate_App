package com.example.hackmate.POJOClasses.DomainTeamsHack;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Final {

    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = null;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}
