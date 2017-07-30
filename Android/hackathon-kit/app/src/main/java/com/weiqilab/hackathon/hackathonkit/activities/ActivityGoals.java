package com.weiqilab.hackathon.hackathonkit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.weiqilab.hackathon.hackathonkit.R;

public class ActivityGoals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        TextView dateView = (TextView) findViewById(R.id.dateView);
        String date = (String) getIntent().getExtras().get("date");
        dateView.setText(date);
    }
}
