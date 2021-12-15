package com.example.skybeatz;

import java.util.ArrayList;

public class Temp {
    public static Temp myObj;

    public Temp(){}

    public static Temp getInstance() {
        if (myObj == null) {
            myObj = new Temp();
        }

        return myObj;
    }

    private String songName;
    private cardAdapter arrayList;

    public String  getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }
    public cardAdapter  getArrayList() {
        return arrayList;
    }
    public void setArrayList(cardAdapter  arrayList) {
        this.arrayList = arrayList;
    }
}