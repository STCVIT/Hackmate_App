package com.example.hackmate.POJOClasses.Kavita.Invites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/*
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
*/


public class Team1 {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}