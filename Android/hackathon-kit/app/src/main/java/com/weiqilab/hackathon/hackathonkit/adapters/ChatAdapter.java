package com.weiqilab.hackathon.hackathonkit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.weiqilab.hackathon.hackathonkit.R;
import com.weiqilab.hackathon.hackathonkit.activities.ActivityChat;
import com.weiqilab.hackathon.hackathonkit.entities.MessagesEntity;

import java.sql.Timestamp;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ajinkyachalke on 7/31/17.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    public ArrayList<MessagesEntity> mDataSet;
    public LayoutInflater mInflater;
    public Context mContext;
    public ActivityChat mActivityChat;

    public ChatAdapter(ArrayList<MessagesEntity> dataSet, Context context){
        this.mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mActivityChat = (ActivityChat)context;

    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == 1)
            v = mInflater.inflate(R.layout.item_message_me, parent, false);
        else
            v = mInflater.inflate(R.layout.item_message, parent, false);

        ChatAdapter.ViewHolder vH = new ChatAdapter.ViewHolder(v);
        return vH;
    }

    @Override
    public int getItemViewType(int position) {
        if(mActivityChat.mSelf_User_ID.equalsIgnoreCase(mDataSet.get(position).mUserId))
            return 1;
        else
            return 2;
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        MessagesEntity me = mDataSet.get(position);
        holder.mMesssageTime.setText((new Timestamp(me.mTimeStamp)).toString());
        holder.mMessageTextView.setText(me.mMessage);
        displayPhotoURL(holder.mCircleImageView,
                me.mUserId.equalsIgnoreCase(mActivityChat.mSelf_User_ID) ? mActivityChat.mSelf_Picture
                : mActivityChat.mReceipent_Picture , mContext);
        holder.mMessengerTextView.setText(me.mUserId.equalsIgnoreCase(mActivityChat.mSelf_User_ID) ?
                mActivityChat.mSelf_Name : mActivityChat.mReceipent_Name);



    }

    public static void displayPhotoURL(final CircleImageView messengerImageView, String photoUrl, Context context) {
        if (photoUrl == null) {
            messengerImageView
                    .setImageDrawable(ContextCompat
                            .getDrawable(context,
                                    R.drawable.ic_account_circle_black_36px));
        } else {
            SimpleTarget target = new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                    messengerImageView.setImageBitmap(bitmap);
                }
            };
            Glide.with(context)
                    .load(photoUrl)
                    .asBitmap()
                    .into(target);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mCircleImageView;
        TextView mMessageTextView;
        TextView mMessengerTextView;
        TextView mMesssageTime;


        public ViewHolder(View v){
            super(v);
            mCircleImageView = (CircleImageView) v.findViewById(R.id.messengerImageView);
            mMessageTextView = (TextView) v.findViewById(R.id.messageTextView);
            mMessengerTextView = (TextView) v.findViewById(R.id.messengerTextView);
            mMesssageTime = (TextView) v.findViewById(R.id.messsageTime);
        }
    }
}
