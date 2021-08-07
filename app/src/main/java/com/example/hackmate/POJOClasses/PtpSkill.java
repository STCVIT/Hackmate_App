package com.example.hackmate.POJOClasses;

import java.util.List;

public class PtpSkill{
    public Participant participant;
    public List<Skill> skills;

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

    public PtpSkill(Participant participant, List<Skill> skills) {
        this.participant = participant;
        this.skills = skills;
    }
}
