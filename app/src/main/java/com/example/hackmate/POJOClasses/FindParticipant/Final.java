package com.example.hackmate.POJOClasses.FindParticipant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Final {

    @SerializedName("pt")
    @Expose
    private Pt pt;
    @SerializedName("skills")
    @Expose
    private List<Skill> skills = null;

    public Pt getPt() {
        return pt;
    }

    public void setPt(Pt pt) {
        this.pt = pt;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

}
