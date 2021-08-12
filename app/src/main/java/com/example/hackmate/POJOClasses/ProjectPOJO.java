package com.example.hackmate.POJOClasses;

import java.util.List;

public class ProjectPOJO {
    public List<IndividualProjectYash> individualProjects;
    public List<TeamProjectYash> teams;

    public ProjectPOJO(List<IndividualProjectYash> individualProjects, List<TeamProjectYash> teams) {
        this.individualProjects = individualProjects;
        this.teams = teams;
    }

    public List<IndividualProjectYash> getIndividualProjects() {
        return individualProjects;
    }

    public void setIndividualProjects(List<IndividualProjectYash> individualProjects) {
        this.individualProjects = individualProjects;
    }

    public List<TeamProjectYash> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamProjectYash> teams) {
        this.teams = teams;
    }
}