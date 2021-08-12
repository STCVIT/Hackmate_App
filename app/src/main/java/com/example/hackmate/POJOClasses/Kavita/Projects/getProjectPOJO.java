package com.example.hackmate.POJOClasses.Kavita.Projects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getProjectPOJO {

    @SerializedName("individualProjects")
    @Expose
    private List<IndividualProject> individualProjects = null;
    @SerializedName("teams")
    @Expose
    private List<TeamProject> teams = null;

    public List<IndividualProject> getIndividualProjects() {
        return individualProjects;
    }

    public void setIndividualProjects(List<IndividualProject> individualProjects) {
        this.individualProjects = individualProjects;
    }

    public List<TeamProject> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamProject> teams) {
        this.teams = teams;
    }

}