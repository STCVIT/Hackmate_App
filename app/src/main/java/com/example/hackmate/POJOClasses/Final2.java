package com.example.hackmate.POJOClassesKavita;





        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


public class Final2 {

    @SerializedName("team")
    @Expose
    private Team team;
    @SerializedName("hackName")
    @Expose
    private String hackName;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getHackName() {
        return hackName;
    }

    public void setHackName(String hackName) {
        this.hackName = hackName;
    }

}