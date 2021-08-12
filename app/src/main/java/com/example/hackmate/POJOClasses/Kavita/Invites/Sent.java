package com.example.hackmate.POJOClasses.Kavita.Invites;




import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/*
public class Sent {

    @SerializedName("_id")
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
    }

}
*/

public class Sent {

    @SerializedName("inv")
    @Expose
    private Inv__1 inv;
    @SerializedName("participant")
    @Expose
    private Participant participant;
    @SerializedName("team")
    @Expose
    private Team1 team;

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

    public Team1 getTeam2() {
        return team;
    }

    public void setTeam2(Team1 team) {
        this.team = team;
    }

}