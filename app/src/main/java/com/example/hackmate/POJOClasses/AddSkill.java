package com.example.hackmate.POJOClasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddSkill {
    @SerializedName("skills")
    private List<String> skills;

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
