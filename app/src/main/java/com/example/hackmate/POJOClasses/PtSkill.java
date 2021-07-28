package com.example.hackmate.POJOClassesKavita;

import java.util.List;

public class PtSkill {
    public Participant participant;
    public List<Object> skills;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public List<Object> getSkills() {
        return skills;
    }

    public void setSkills(List<Object> skills) {
        this.skills = skills;
    }
}
