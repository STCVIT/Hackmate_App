package com.example.hackmate.POJOClassesKavita;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/*
public class myTeamsPOJO {


    @SerializedName("teamname")
    private String teamName;
    @SerializedName("hackname")
    private int hackName;
    @SerializedName("position")
    private String teamPosition;
    @SerializedName("skills")
    private String[] domain;

    private String domain1= domain[1];
    private String domain2= domain[1];
    private String domain3= domain[1];
    private String domain4= domain[1];
    private String domain5= domain[1];
    public String getTeamName() {
        return teamName;
    }

    public int getHackName() {
        return hackName;
    }

    public String getTeamPosition() {
        return teamPosition;
    }

    public String getDomain1() {
        return  domain1;
    }
    public String getDomain2() {
        return  domain2 ;
    }
    public String getDomain3() {
        return  domain3;
    }
    public String getDomain4() {
        return   domain4;
    }
    public String getDomain5() {
        return  domain5 ;
    }

}
*/

        import java.util.List;


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

    public void setFinal2(List<Final2> _final) {
        this._final = _final;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}