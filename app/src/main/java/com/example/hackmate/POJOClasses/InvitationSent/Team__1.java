package com.example.hackmate.POJOClasses.InvitationSent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team__1 {

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
