package com.example.hackmate.POJOClasses.POST;

public class LoginDetails {

    String name, college, github, linkedIn, website, photo, bio, username;
    int graduation_year;

    public LoginDetails(String name, String college, String github, String linkedIn, String website, String photo, String bio, int graduation_year, String username) {
        this.name = name;
        this.college = college;
        this.github = github;
        this.linkedIn = linkedIn;
        this.website = website;
        this.photo = photo;
        this.bio = bio;
        this.username = username;
        this.graduation_year = graduation_year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGraduation_year() {
        return graduation_year;
    }

    public void setGraduation_year(int graduation_year) {
        this.graduation_year = graduation_year;
    }
}
