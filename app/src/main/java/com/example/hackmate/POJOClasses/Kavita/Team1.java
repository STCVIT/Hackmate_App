package com.example.hackmate.POJOClasses.Kavita;

import com.example.hackmate.POJOClasses.Member;

import java.util.List;

public class Team1 {


    public String _id;
    public String name;
    public String admin_id;
    public String team_code;
    public List<com.example.hackmate.POJOClasses.Member> members;
    public String hack_id;
    public int __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getTeam_code() {
        return team_code;
    }

    public void setTeam_code(String team_code) {
        this.team_code = team_code;
    }

    public List<com.example.hackmate.POJOClasses.Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getHack_id() {
        return hack_id;
    }

    public void setHack_id(String hack_id) {
        this.hack_id = hack_id;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}

