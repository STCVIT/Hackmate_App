package com.example.hackmate.POJOClasses;

import java.util.List;

public class JoinTeamPOJO {

    public Team team;
    public List<PtSkill> pt_skills;

    public Team getTeam() {

        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<PtSkill> getPt_skills() {
        return pt_skills;
    }

    public void setPt_skills(List<PtSkill> pt_skills) {
        this.pt_skills = pt_skills;
    }
}



