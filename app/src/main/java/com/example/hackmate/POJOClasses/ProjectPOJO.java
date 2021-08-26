package com.example.hackmate.POJOClasses;

import java.util.List;

public class ProjectPOJO {
    public List<IndividualProject> individualProjects;
    public List<TeamProject> teams;

    public ProjectPOJO(List<IndividualProject> individualProjects, List<TeamProject> teams) {
        this.individualProjects = individualProjects;
        this.teams = teams;
    }

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
