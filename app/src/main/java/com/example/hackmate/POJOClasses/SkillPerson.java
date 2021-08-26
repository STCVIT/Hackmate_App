package com.example.hackmate.POJOClasses;

import java.util.List;

public class SkillPerson {

    List<Skill> skillList;

    public SkillPerson(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }
}
