package com.example.hackmate.POJOClasses.Kavita.Projects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IndividualProject {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("participant_id")
    @Expose
    private String participantId;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("demonstration")
    @Expose
    private String demonstration;
    @SerializedName("design")
    @Expose
    private String design;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String getDemonstration() {
        return demonstration;
    }

    public void setDemonstration(String demonstration) {
        this.demonstration = demonstration;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

}