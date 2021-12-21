package com.example.skybeatz;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ArraySaver implements Parcelable {
    String searchString;
    ArrayList<cardModel> arrayList;

    protected ArraySaver(Parcel in) {
    }

    public static final Creator<ArraySaver> CREATOR = new Creator<ArraySaver>() {
        @Override
        public ArraySaver createFromParcel(Parcel in) {
            return new ArraySaver(in);
        }

        @Override
        public ArraySaver[] newArray(int size) {
            return new ArraySaver[size];
        }
    };

    public ArraySaver(String searchString, ArrayList<cardModel> arrayList) {
        this.searchString = searchString;
        this.arrayList = arrayList;
    }

    public ArrayList<cardModel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<cardModel> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
