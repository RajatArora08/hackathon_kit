package com.weiqilab.hackathon.hackathonkit.activities;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;
import com.weiqilab.hackathon.hackathonkit.BR;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class UserEntity extends BaseObservable {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("clicked")
    private int mClicked;


    public UserEntity() {}


    public UserEntity(String id, String name, int clicked) {
        this.id = id;
        this.name = name;
        this.mClicked = clicked;
    }


    @Bindable
    public int getClicked() {
        return mClicked;
    }


    public void setClicked(int newValue) {
        mClicked = newValue;
        notifyPropertyChanged(BR.clicked);
    }
}
