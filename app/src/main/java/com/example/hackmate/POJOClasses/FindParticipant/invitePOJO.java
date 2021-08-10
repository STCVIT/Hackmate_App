package com.example.hackmate.POJOClasses.FindParticipant;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class invitePOJO {
    @SerializedName("final")
    @Expose
    private List<FinalPt> _finalPt = null;
    @SerializedName("length")
    @Expose
    private Integer length;

    public List<FinalPt> getFinal() {
        return _finalPt;
    }

    public void setFinal(List<FinalPt> _finalPt) {
        this._finalPt = _finalPt;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}

