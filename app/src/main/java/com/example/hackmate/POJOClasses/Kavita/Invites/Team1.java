package com.example.hackmate.POJOClasses.Kavita.Invites;

import com.example.hackmate.POJOClasses.Kavita.Invites.Member__1;

import java.util.List;

/*public class Team1 {


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
}*/


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Team1 {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("team_code")
    @Expose
    private String teamCode;
    @SerializedName("members")
    @Expose
    private List<Member__1> members = null;
    @SerializedName("hack_id")
    @Expose
    private String hackId;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public List<Member__1> getMembers() {
        return members;
    }

    public void setMembers(List<Member__1> members) {
        this.members = members;
    }

    public String getHackId() {
        return hackId;
    }

    public void setHackId(String hackId) {
        this.hackId = hackId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}
