package com.example.skybeatz;

public class Artist {
    private String name;
    private String id;
    private String profile;

    public Artist(String name, String id, String profile) {
        this.name = name;
        this.id = id;
        this.profile = profile;
    }

    public Artist() {

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getProfile() {
        return profile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
