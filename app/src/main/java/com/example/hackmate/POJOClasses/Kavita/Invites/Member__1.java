package com.example.hackmate.POJOClasses.Kavita.Invites;



        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Member__1 {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("uid")
    @Expose
    private String uid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}