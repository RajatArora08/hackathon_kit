package com.weiqilab.hackathon.hackathonkit.entities;

import android.databinding.BaseObservable;

import java.util.List;

/**
 * Created by ajinkyachalke on 7/29/17.
 */

public class ChatEntity extends BaseObservable {

    public List<MessagesEntity> mMessages;

    public ChatEntity(List<MessagesEntity> mMessages) {
        this.mMessages = mMessages;
    }
}
