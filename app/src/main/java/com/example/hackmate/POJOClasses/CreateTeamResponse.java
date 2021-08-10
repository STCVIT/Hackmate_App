package com.example.hackmate.POJOClasses;

import com.google.gson.annotations.SerializedName;

public class CreateTeamResponse {
    @SerializedName("_id")
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
