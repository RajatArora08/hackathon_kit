package com.weiqilab.hackathon.hackathonkit.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.weiqilab.hackathon.hackathonkit.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityGoals extends AppCompatActivity {

    private CircleImageView mImage1;
    private CircleImageView mImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        mImage1 = (CircleImageView) findViewById(R.id.imageView);
        mImage2 = (CircleImageView) findViewById(R.id.imageView2);

        String url = (String)getIntent().getExtras().get("pic_1");
        String url2 = (String)getIntent().getExtras().get("pic_2");

        displayPhotoURL(mImage1, url, this);
        displayPhotoURL(mImage2, url2, this);


//        TextView dateView = (TextView) findViewById(R.id.dateView);
//        String date = (String) getIntent().getExtras().get("date");
//        dateView.setText(date);
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
}
