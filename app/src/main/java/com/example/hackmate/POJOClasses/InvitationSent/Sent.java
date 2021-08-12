package com.example.hackmate.POJOClasses.InvitationSent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sent {

    @SerializedName("inv")
    @Expose
    private Inv__1 inv;
    @SerializedName("participant")
    @Expose
    private Participant participant;
    @SerializedName("team")
    @Expose
    private Team__1 team;

    public Inv__1 getInv() {
        return inv;
    }

    public void setInv(Inv__1 inv) {
        this.inv = inv;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Team__1 getTeam() {
        return team;
    }

    public void setTeam(Team__1 team) {
        this.team = team;
    }
}
