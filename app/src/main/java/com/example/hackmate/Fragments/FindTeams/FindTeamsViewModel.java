package com.example.hackmate.Fragments.FindTeams;

import androidx.lifecycle.ViewModel;

import com.example.hackmate.POJOClasses.JoinHackTeams.Final;

import java.util.List;

public class FindTeamsViewModel extends ViewModel {

    private List<Final> list;
    private String name, skill;

    public List<Final> getList() {
        return list;
    }

    public void setList(List<Final> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
    public void addItems(List<Final> list){
        this.list.addAll(list);
    }

}
