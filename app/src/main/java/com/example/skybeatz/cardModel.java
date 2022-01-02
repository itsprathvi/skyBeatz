package com.example.skybeatz;

import android.os.Parcel;
import android.os.Parcelable;

public class cardModel implements Parcelable {

    String name,prev_url, artistName, artist_id, img_url;

    public cardModel(String name, String prev_url, String artistName, String artist_id, String img_url) {
        this.name = name;
        this.prev_url = prev_url;
        this.artistName = artistName;
        this.artist_id = artist_id;
        this.img_url = img_url;
    }

    protected cardModel(Parcel in) {
        name = in.readString();
        prev_url = in.readString();
        artistName = in.readString();
        artist_id = in.readString();
        img_url = in.readString();
    }

    public static final Creator<cardModel> CREATOR = new Creator<cardModel>() {
        @Override
        public cardModel createFromParcel(Parcel in) {
            return new cardModel(in);
        }

        @Override
        public cardModel[] newArray(int size) {
            return new cardModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(prev_url);
        dest.writeString(artistName);
        dest.writeString(artist_id);
        dest.writeString(img_url);

    }
}
