package com.example.hackmate.POJOClasses;

import com.example.hackmate.POJOClasses.Skill;
import com.example.hackmate.POJOClasses.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchAndJoinHackPOJO {
    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;

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
}

