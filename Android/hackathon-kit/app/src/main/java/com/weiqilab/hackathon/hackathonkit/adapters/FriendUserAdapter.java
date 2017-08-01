package com.weiqilab.hackathon.hackathonkit.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.weiqilab.hackathon.hackathonkit.MyApplication;
import com.weiqilab.hackathon.hackathonkit.R;
import com.weiqilab.hackathon.hackathonkit.activities.ActivityChat;
import com.weiqilab.hackathon.hackathonkit.activities.ActivityMain;
import com.weiqilab.hackathon.hackathonkit.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rapid.ListUpdate;
import io.rapid.Rapid;
import io.rapid.RapidCallback;
import io.rapid.RapidCollectionSubscription;
import io.rapid.RapidDocument;

/**
 * Created by ajinkyachalke on 7/31/17.
 */
public class FriendUserAdapter extends RecyclerView.Adapter<FriendUserAdapter.ViewHolder>{

    public ArrayList<UserEntity> mDataSet;
    public LayoutInflater mInflater;
    public Context mContext;

    public FriendUserAdapter(ArrayList<UserEntity> dataSet, Context context){
        this.mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @Override
    public FriendUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO inflate layout here
        View v = mInflater.inflate(R.layout.custom_user_item, parent, false);
        FriendUserAdapter.ViewHolder vH = new FriendUserAdapter.ViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(FriendUserAdapter.ViewHolder holder, int position) {

        // TODO assign layout values here
        UserEntity me = mDataSet.get(position);
        holder.mUserNameTextView.setText(me.mProfileEntity.mName);
        holder.mUsercityTextView.setText(me.mProfileEntity.mCity);
        holder.mUserinterestsTextView.setText(android.text.TextUtils.join(", ", me.mInterests));
        displayPhotoURL(holder.mCircleImageView, me.mProfileEntity.mPicture, mContext);
        holder.mUser = me;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    // TODO display profile picture here.
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

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, RapidCallback.Document<UserEntity>{

        // TODO create fields here for layout.
        CircleImageView mCircleImageView;
        TextView mUserNameTextView;
        TextView mUsercityTextView;
        TextView mUserinterestsTextView;
        UserEntity mUser;
        private RapidCollectionSubscription mSubscription;

        public ViewHolder(View v){
            super(v);
            mCircleImageView = (CircleImageView) v.findViewById(R.id.userthumbnail);
            mUserNameTextView = (TextView) v.findViewById(R.id.username);
            mUsercityTextView = (TextView) v.findViewById(R.id.usercity);
            mUserinterestsTextView = (TextView) v.findViewById(R.id.userinterests);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Rapid.getInstance().collection("motiv8-users-mockup", UserEntity.class)
                    .document(mUser.mProfileEntity.mUserId)
                    .fetch(this);


        }

        @Override
        public void onValueChanged(RapidDocument<UserEntity> document) {
            Intent intent = new Intent(MyApplication.getAppContext(), ActivityChat.class);
            intent.putExtra(ActivityMain.SELF_USER_ID, "0001");
            intent.putExtra(ActivityMain.SELF_PICTURE, "https://lh3.googleusercontent.com/-oTueqvCzT6Q/AAAAAAAAAAI/AAAAAAAAXBk/csB7XzK-un8/s96-c/photo.jpg");
            intent.putExtra(ActivityMain.CHAT_ID,"11101");
            intent.putExtra(ActivityMain.SELF_USER_NAME,"Rajat");
            intent.putExtra(ActivityMain.RECEIPENT_USER_ID, document.getBody().mProfileEntity.mUserId);
            intent.putExtra(ActivityMain.RECEIPENT_PICTURE, document.getBody().mProfileEntity.mPicture);
            intent.putExtra(ActivityMain.RECEIPENT_USER_NAME,document.getBody().mProfileEntity.mName);
            MyApplication.getAppContext().startActivity(intent);
        }
    }
}
