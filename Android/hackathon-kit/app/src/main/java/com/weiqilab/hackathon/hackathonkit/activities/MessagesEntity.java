package com.weiqilab.hackathon.hackathonkit.activities;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class MessagesEntity extends BaseObservable {

    @SerializedName("userid")
    public String mUserId;

    @SerializedName("message")
    public String mText;

    @SerializedName("timestamp")
    public  long mTimeStamp;

    public MessagesEntity(String mUserId, String mText, long mTimeStamp) {
        this.mUserId = mUserId;
        this.mText = mText;
        this.mTimeStamp = mTimeStamp;
    }
}
