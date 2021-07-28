package com.example.hackmate.POJOClassesKavita;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class teamIdPOJO {
    @SerializedName(("team"))
    public Team1 team1;
    public List<PtSkill> pt_skills;

    public Team1 getTeam1() {

        return team1;
    }

    public void setTeam1(Team1 team1) {
        this.team1 = team1;
    }

    public List<PtSkill> getPt_skills() {
        return pt_skills;
    }

    public void setPt_skills(List<PtSkill> pt_skills) {
        this.pt_skills = pt_skills;
    }

}
