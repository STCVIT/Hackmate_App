package com.example.hackmate.POJOClassesKavita;

import com.google.gson.annotations.SerializedName;

public class addProjectPOJO {

    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;

    public addProjectPOJO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
