package com.example.hackmate.Models;

public class AddFromExistingModel {

    String projectName, description, designation;

    public AddFromExistingModel(String projectName, String description, String designation) {
        this.projectName = projectName;
        this.description = description;
        this.designation = designation;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
