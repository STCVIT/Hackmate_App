package com.example.hackmate.POJOClasses.Kavita.Invites;


import com.example.hackmate.POJOClasses.Kavita.myTeams.Team;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Received {

    @SerializedName("inv")
    @Expose
    private Inv inv;
    @SerializedName("leader")
    @Expose
    private Leader leader;
    @SerializedName("team")
    @Expose
    private TeamInvite team;

    public Inv getInv() {
        return inv;
    }

    public void setInv(Inv inv) {
        this.inv = inv;
    }

    public Leader getLeader() {
        return leader;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public TeamInvite getTeam() {
        return team;
    }

    public void setTeam(TeamInvite team) {
        this.team = team;
    }

 /*   @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("team_id")
    @Expose
    private String teamId;
    @SerializedName("participant_id")
    @Expose
    private String participantId;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }*/

}