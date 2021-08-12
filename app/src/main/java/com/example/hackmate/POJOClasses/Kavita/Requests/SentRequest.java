package com.example.hackmate.POJOClasses.Kavita.Requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
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
