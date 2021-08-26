package com.example.hackmate.POJOClasses.Kavita.Requests;

import com.example.hackmate.POJOClasses.Kavita.Invites.LeaderInvite;
import com.example.hackmate.POJOClasses.Kavita.Invites.Team1;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
public class SentRequest {
    @SerializedName("req")
    @Expose
    private Req__1 req;
    @SerializedName("team")
    @Expose
    private Object team;

    public Req__1 getReq() {
        return req;
    }

    public void setReq(Req__1 req) {
        this.req = req;
    }

    public Object getTeam() {
        return team;
    }

    public void setTeam(Object team) {
        this.team = team;
    }

}
*/


public class SentRequest {

    @SerializedName("req")
    @Expose
    private String req;
    @SerializedName("leader")
    @Expose
    private LeaderInvite leader;
    @SerializedName("team")
    @Expose
    private Team1 team;


    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public LeaderInvite getLeader() {
        return leader;
    }

    public void setLeader(LeaderInvite leader) {
        this.leader = leader;
    }

    public Team1 getTeam() {
        return team;
    }

    public void setTeam(Team1 team) {
        this.team = team;
    }

}