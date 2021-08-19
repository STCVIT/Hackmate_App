package com.example.hackmate.POJOClasses.Kavita.Projects.addOReditProject;

import com.google.gson.annotations.SerializedName;

/*
public class addProjectPOJO {

    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;

    public addProjectPOJO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
*/
public class addProjectPOJO {
    /*@SerializedName("_id")
    private String projectID;*/

    @SerializedName("project_name")
    private String projectName;
    @SerializedName("project_description")
    private  String description;
    @SerializedName("code")
    private  String githubLink;
    @SerializedName("design")
    private  String designLink;
    @SerializedName("demonstration")
    private  String demoLink;

    public addProjectPOJO(String projectName, String description, String githubLink, String designLink, String demoLink) {
        this.projectName = projectName;
        this.description = description;
        this.githubLink = githubLink;
        this.designLink = designLink;
        this.demoLink = demoLink;
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

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }

    public String getDesignLink() {
        return designLink;
    }

    public void setDesignLink(String designLink) {
        this.designLink = designLink;
    }

    public String getDemoLink() {
        return demoLink;
    }

    public void setDemoLink(String demoLink) {
        this.demoLink = demoLink;
    }

   /* public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }*/
}