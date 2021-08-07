package com.example.hackmate.POJOClasses;

import java.util.List;

public class TeamDetails {
    public Team team;
    public String hackName;
    public List<PtpSkill> ptp_skill;

    public TeamDetails(Team team, String hackName, List<PtpSkill> ptp_skill) {
        this.team = team;
        this.hackName = hackName;
        this.ptp_skill = ptp_skill;
    }

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

    public List<PtpSkill> getPtp_skill() {
        return ptp_skill;
    }

    public void setPtp_skill(List<PtpSkill> ptp_skill) {
        this.ptp_skill = ptp_skill;
    }
}
