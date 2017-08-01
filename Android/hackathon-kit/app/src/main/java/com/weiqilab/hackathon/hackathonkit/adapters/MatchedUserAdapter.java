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
import com.weiqilab.hackathon.hackathonkit.entities.UserEntity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ajinkyachalke on 7/31/17.
 */
public class MatchedUserAdapter extends RecyclerView.Adapter<MatchedUserAdapter.ViewHolder>{

    public ArrayList<UserEntity> mDataSet;
    public LayoutInflater mInflater;
    public Context mContext;

    public MatchedUserAdapter(ArrayList<UserEntity> dataSet, Context context){
        this.mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @Override
    public MatchedUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO inflate layout here
        View v = mInflater.inflate(R.layout.custom_user_item, parent, false);
        MatchedUserAdapter.ViewHolder vH = new MatchedUserAdapter.ViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(MatchedUserAdapter.ViewHolder holder, int position) {

        // TODO assign layout values here
        UserEntity me = mDataSet.get(position);
        holder.mUserNameTextView.setText(me.mProfileEntity.mName);
        holder.mUsercityTextView.setText(me.mProfileEntity.mCity);
        holder.mUserinterestsTextView.setText(android.text.TextUtils.join(", ", me.mInterests));
        displayPhotoURL(holder.mCircleImageView, me.mProfileEntity.mPicture, mContext);
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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // TODO create fields here for layout.
        CircleImageView mCircleImageView;
        TextView mUserNameTextView;
        TextView mUsercityTextView;
        TextView mUserinterestsTextView;

        public ViewHolder(View v){
            super(v);
            mCircleImageView = (CircleImageView) v.findViewById(R.id.userthumbnail);
            mUserNameTextView = (TextView) v.findViewById(R.id.username);
            mUsercityTextView = (TextView) v.findViewById(R.id.usercity);
            mUserinterestsTextView = (TextView) v.findViewById(R.id.userinterests);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
