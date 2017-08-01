package com.weiqilab.hackathon.hackathonkit.entities;

import android.databinding.BaseObservable;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class MessagesEntity extends BaseObservable {

    @SerializedName("chat-id")
    public String mChat_ID;

    @SerializedName("userid")
    public String mUserId;

    @SerializedName("message")
    public String mMessage;

    @SerializedName("timestamp")
    public  long mTimeStamp;

    @SerializedName("picture")
    public String picture;

    public MessagesEntity(String chat_ID, String mUserId, String mText, long mTimeStamp) {
        this.mChat_ID = chat_ID;
        this.mUserId = mUserId;
        this.mMessage = mText;
        this.mTimeStamp = mTimeStamp;
    }
}
