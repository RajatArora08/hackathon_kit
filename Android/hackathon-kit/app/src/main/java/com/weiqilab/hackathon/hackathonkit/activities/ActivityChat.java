package com.weiqilab.hackathon.hackathonkit.activities;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiqilab.hackathon.hackathonkit.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivityChat extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);



    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>{

    public List<MessagesEntity> mDataSet;
    public LayoutInflater mInflater;

    public CustomAdapter(List<MessagesEntity> dataSet, Context context){
        this.mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_message, parent, false);
        CustomAdapter.ViewHolder vH = new CustomAdapter.ViewHolder(v);
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mCircleImageView;
        TextView mMessageTextView;
        TextView mMessengerTextView;
        TextView mMesssageTime;


        public ViewHolder(View v){
            super(v);


        }
    }
}
