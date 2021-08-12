package com.example.hackmate.POJOClasses.Kavita.myTeams;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Final2 {

    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("hackName")
    @Expose
    private String hackName;
    @SerializedName("pt_skill")
    @Expose
    private List<PtSkill2> ptSkill = null;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getHackName() {
        return hackName;
    }

    public void setHackName(String hackName) {
        this.hackName = hackName;
    }

    public List<PtSkill2> getPtSkill() {
        return ptSkill;
    }

    public void setPtSkill(List<PtSkill2> ptSkill) {
        this.ptSkill = ptSkill;
    }

}