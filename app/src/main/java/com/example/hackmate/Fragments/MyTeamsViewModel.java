package com.example.hackmate.Fragments;

import androidx.lifecycle.ViewModel;

import com.example.hackmate.POJOClasses.Kavita.myTeams.Final2;

import java.util.List;

public class MyTeamsViewModel extends ViewModel {
    private List<Final2> list;
    private String id;

    public List<Final2> getList() {
        return list;
    }

    public void setList(List<Final2> list) {
        this.list = list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
