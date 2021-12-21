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
    private cardAdapter cardAdptr;
    private ArrayList<cardModel> arrayList = new ArrayList<>();

    public String  getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }
    public cardAdapter  getCardAdptr() {
        return cardAdptr;
    }
    public void setCardAdptr(cardAdapter  arrayList) {
        this.cardAdptr = arrayList;
    }

    public void setArrayList(ArrayList<cardModel> obj){
        this.arrayList = obj;
    }

    public ArrayList<cardModel> getArrayList(){
        return  this.arrayList;
    }
}