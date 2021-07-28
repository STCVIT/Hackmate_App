package com.example.hackmate.POJOClassesKavita;

import com.google.gson.annotations.SerializedName;

/*public class invitePOJO {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String ParticipantName;
    @SerializedName("teamname")
    private int teamName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParticipantName() {
        return ParticipantName;
    }

    public void setParticipantName(String participantName) {
        ParticipantName = participantName;
    }

    public int getTeamName() {
        return teamName;
    }

    public void setTeamName(int teamName) {
        this.teamName = teamName;
    }
}
*/



import java.util.List;

import com.google.gson.annotations.Expose;


public class invitePOJO {

    @SerializedName("received")
    @Expose
    private List<Received> received = null;
    @SerializedName("sent")
    @Expose
    private List<Sent> sent = null;

    public List<Received> getReceived() {
        return received;
    }

    public void setReceived(List<Received> received) {
        this.received = received;
    }

    public List<Sent> getSent() {
        return sent;
    }

    public void setSent(List<Sent> sent) {
        this.sent = sent;
    }

}