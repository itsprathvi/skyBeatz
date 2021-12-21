package com.example.skybeatz;

public class cardModel {

    String name,prev_url, artistName, artist_id, img_url;

    public cardModel(String name, String prev_url, String artistName, String artist_id, String img_url) {
        this.name = name;
        this.prev_url = prev_url;
        this.artistName = artistName;
        this.artist_id = artist_id;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrev_url() {
        return prev_url;
    }

    public void setPrev_url(String prev_url) {
        this.prev_url = prev_url;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
