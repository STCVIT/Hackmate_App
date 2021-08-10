package com.example.hackmate.Fragments.FindParticipant;

import androidx.lifecycle.ViewModel;

import com.example.hackmate.POJOClasses.FindParticipant.FinalPt;

import java.util.List;

public class FindParticipantViewModel  extends ViewModel {

    private List<FinalPt> list;
    private String name, skill;

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

    public List<FinalPt> getList() {
        return list;
    }
    public void setList(List<FinalPt> list) {
        this.list = list;
    }
    public void clearList(){
        this.list.clear();
    }
    /*
    public void addItems(List<FinalPt> list){
        this.list.addAll(list);
    }

     */
}
