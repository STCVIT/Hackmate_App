package com.example.hackmate.POJOClasses.Kavita.Invites;

public class AcceptPOJO {
    public String  id;
    public String status;

    public AcceptPOJO(String ID, String accepted) {
        this.id=ID;
        this.status=accepted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
