package com.example.hackmate.POJOClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMyTeamPOJO {

    @SerializedName("final")
    @Expose
    public List<TeamDetails> teamDetails;

    public int length;


    public GetMyTeamPOJO(List<TeamDetails> teamDetails, int length) {
        this.teamDetails = teamDetails;
        this.length = length;
    }

    public List<TeamDetails> getTeamDetails() {
        return teamDetails;
    }

    public void setTeamDetails(List<TeamDetails> teamDetails) {
        this.teamDetails = teamDetails;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


}



