package com.weiqilab.hackathon.hackathonkit.activities;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class FriendsEntity extends BaseObservable {
    public FriendsEntity(String mUserId, String mStatus, String mCommonInterest) {
        this.mUserId = mUserId;
        this.mStatus = mStatus;
        this.mCommonInterest = mCommonInterest;
    }

    @SerializedName("userid")
    public String mUserId;

    @SerializedName("status")
    public String mStatus;

    @SerializedName("common-interest")
    public String mCommonInterest;

}
