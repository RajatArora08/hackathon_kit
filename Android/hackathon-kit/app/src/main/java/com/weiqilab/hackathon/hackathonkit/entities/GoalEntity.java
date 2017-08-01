package com.weiqilab.hackathon.hackathonkit.entities;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class GoalEntity extends BaseObservable {
    public GoalEntity(String mGoalId, String mActivityName, Date mStartDate, String mDays) {
        this.mGoalId = mGoalId;
        this.mActivityName = mActivityName;
        this.mStartDate = mStartDate;
        this.mDays = mDays;
    }

    @SerializedName("goal-id")
    public String mGoalId;

    @SerializedName("activity-name")
    public String mActivityName;

    @SerializedName("start-date")
    public Date mStartDate;

    @SerializedName("days")
    public String mDays;

}


