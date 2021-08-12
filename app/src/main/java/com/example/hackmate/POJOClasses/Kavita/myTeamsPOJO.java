package com.example.hackmate.POJOClasses.Kavita;

import com.example.hackmate.POJOClasses.Kavita.myTeams.Final2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/*public class myTeamsPOJO {

    @SerializedName("final")
    @Expose
    private List<Final2> _final = null;
    @SerializedName("length")
    @Expose
    private Integer length;

    public List<Final2> getFinal2() {
        return _final;
    }

    public void setFinal2(List<Final2> _final) {
        this._final = _final;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}*/


public class myTeamsPOJO {

    @SerializedName("final")
    @Expose
    private List<Final2> _final = null;
    @SerializedName("length")
    @Expose
    private Integer length;

    public List<Final2> getFinal2() {
        return _final;
    }

    public void setFinal(List<Final2> _final) {
        this._final = _final;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}