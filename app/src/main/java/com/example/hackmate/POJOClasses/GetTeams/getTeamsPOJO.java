package com.example.hackmate.POJOClasses.GetTeams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getTeamsPOJO {
    @SerializedName("final")
    @Expose
    private List<Final> _final = null;
    @SerializedName("length")
    @Expose
    private Integer length;

    public List<Final> getFinal() {
        return _final;
    }

    public void setFinal(List<Final> _final) {
        this._final = _final;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
