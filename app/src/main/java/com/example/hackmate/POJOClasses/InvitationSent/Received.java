package com.example.hackmate.POJOClasses.InvitationSent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Received {

    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("leader")
    @Expose
    private Leader leader;
    @SerializedName("inv")
    @Expose
    private String inv;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public String getInv() {
        return inv;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

}
