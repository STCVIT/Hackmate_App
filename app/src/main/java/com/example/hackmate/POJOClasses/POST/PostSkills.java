package com.example.hackmate.POJOClasses.POST;

import java.util.List;

public class PostSkills {

    List<String> skills;

    public PostSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
