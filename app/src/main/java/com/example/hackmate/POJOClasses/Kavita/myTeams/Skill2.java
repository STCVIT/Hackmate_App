package com.example.hackmate.POJOClasses.Kavita.myTeams;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Skill2 {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("skill")
    @Expose
    private String skill;
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

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
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