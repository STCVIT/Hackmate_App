package com.example.hackmate.POJOClasses.FindPtByName;

import com.example.hackmate.POJOClasses.Participant;
import com.example.hackmate.POJOClasses.Skill;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Final {

    @SerializedName("participant")
    @Expose
    private Participant participant;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

}
