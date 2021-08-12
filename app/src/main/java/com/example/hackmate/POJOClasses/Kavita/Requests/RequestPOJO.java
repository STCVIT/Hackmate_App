package com.example.hackmate.POJOClasses.Kavita.Requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestPOJO {
    @SerializedName("received")
    @Expose
    private List<ReceivedRequest> received = null;
    @SerializedName("sent")
    @Expose
    private List<SentRequest> sent = null;

    public List<ReceivedRequest> getReceived() {
        return received;
    }

    public void setReceived(List<ReceivedRequest> received) {
        this.received = received;
    }

    public List<SentRequest> getSent() {
        return sent;
    }

    public void setSent(List<SentRequest> sent) {
        this.sent = sent;
    }

}