package com.example.musicapp;

public class cardModel {

    public String artist;
    public int imgid;
    public String trackname;
    public String moviename;

    public cardModel(String artist, int imgid, String trackname, String moviename) {
        this.artist = artist;
        this.imgid = imgid;
        this.trackname = trackname;
        this.moviename = moviename;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getTrackName() {
        return trackname;
    }

    public void setTrackName(String trackname) {
        this.trackname = trackname;
    }

    public String getMovie() {
        return moviename;
    }

    public void setMovie(String moviename) {
        this.moviename = moviename;
    }
}
