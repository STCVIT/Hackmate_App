package com.example.hackmate.POJOClasses.InvitationSent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Invites {

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

