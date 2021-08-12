package com.example.hackmate.POJOClasses.Kavita.myTeams;


import java.util.List;

import com.example.hackmate.POJOClasses.Kavita.myTeams.Participant2;
import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


public class PtSkill2 {

    @SerializedName("participant")
    @Expose
    private Participant2 participant;
    @SerializedName("skills")
    @Expose
    private List<Skill2> skills = null;

    public Participant2 getParticipant() {
        return participant;
    }

    public void setParticipant(Participant2 participant) {
        this.participant = participant;
    }

    public List<Skill2> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill2> skills) {
        this.skills = skills;
    }

}