package com.example.hackmate.POJOClasses.Kavita.Requests;



        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


public class ReceivedRequest {

    @SerializedName("req")
    @Expose
    private Req req;
    @SerializedName("participant")
    @Expose
    private ParticipantRequest participantRequest;
    @SerializedName("team")
    @Expose
    private TeamRequest team;

    public Req getReq() {
        return req;
    }

    public void setReq(Req req) {
        this.req = req;
    }

    public ParticipantRequest getParticipant() {
        return participantRequest;
    }

    public void setParticipant(ParticipantRequest participantRequest) {
        this.participantRequest =participantRequest;
    }

    public TeamRequest getTeam() {
        return team;
    }

    public void setTeam(TeamRequest team) {
        this.team = team;
    }

}