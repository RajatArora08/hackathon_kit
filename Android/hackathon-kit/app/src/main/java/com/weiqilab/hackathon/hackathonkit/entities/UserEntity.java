package com.weiqilab.hackathon.hackathonkit.entities;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class UserEntity extends BaseObservable {

    @SerializedName("profile")
    public ProfileEntity mProfileEntity;

    @SerializedName("interests")
    public List<String> mInterests;

    public UserEntity(ProfileEntity mProfileEntity, List<String> mInterests, List<FriendsEntity> mFriendsEntity, GoalEntity mGoals) {
        this.mProfileEntity = mProfileEntity;
        this.mInterests = mInterests;
        this.mFriendsEntity = mFriendsEntity;
        this.mGoals = mGoals;
    }

    @SerializedName("friends")
    public List<FriendsEntity> mFriendsEntity;

    @SerializedName("goals")
    public GoalEntity mGoals;

    public UserEntity() {}



}
