package com.example.hackmate;

public class ProjectModel {

    String projectName, description, bio, link1, link2, link3;

    public ProjectModel(String projectName, String description, String bio, String link1, String link2, String link3) {
        this.projectName = projectName;
        this.description = description;
        this.bio = bio;
        this.link1 = link1;
        this.link2 = link2;
        this.link3 = link3;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLink1() {
        return link1;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public String getLink2() {
        return link2;
    }

    public void setLink2(String link2) {
        this.link2 = link2;
    }

    public String getLink3() {
        return link3;
    }

    public void setLink3(String link3) {
        this.link3 = link3;
    }
}
