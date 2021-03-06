package com.weiqilab.hackathon.hackathonkit.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.weiqilab.hackathon.hackathonkit.R;
import com.weiqilab.hackathon.hackathonkit.adapters.ChatAdapter;
import com.weiqilab.hackathon.hackathonkit.entities.MessagesEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.rapid.ListUpdate;
import io.rapid.Rapid;
import io.rapid.RapidCallback;
import io.rapid.RapidCollectionSubscription;
import io.rapid.RapidDocument;


public class ActivityChat extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private RapidCollectionSubscription mSubscription;
    private ArrayList<MessagesEntity> mItems = new ArrayList<MessagesEntity>();
    public String mSelf_User_ID;
    public String mSelf_Picture;
    public String mReceipent_User_ID;
    public String mReceipent_Picture;
    public String mChat_ID;
    public ChatAdapter mAdapter;
    public EditText mMessageEditText;
    public FloatingActionButton mSendButton;
    public RecyclerView mRecyclerView;
    public String mSelf_Name;
    public String mReceipent_Name;
    private ImageButton mImageButton;
    private boolean mAutoHighlight;
    private Button mGoalButton;

    public static final int MSG_LENGTH_LIMIT = 50;
    public static final String COLLECTION = "motiv8-chat-mockup-2";
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        mSelf_User_ID = intent.getStringExtra(ActivityMain.SELF_USER_ID);
        mSelf_Picture = intent.getStringExtra(ActivityMain.SELF_PICTURE);
        mReceipent_User_ID = intent.getStringExtra(ActivityMain.RECEIPENT_USER_ID);
        mReceipent_Picture = intent.getStringExtra(ActivityMain.RECEIPENT_PICTURE);
        mChat_ID = intent.getStringExtra(ActivityMain.CHAT_ID);
        mSelf_Name = intent.getStringExtra(ActivityMain.SELF_USER_NAME);
        mReceipent_Name = intent.getStringExtra(ActivityMain.RECEIPENT_USER_NAME);

        setUpAdapter();

        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(MSG_LENGTH_LIMIT)});
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mSendButton = (FloatingActionButton) findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Send messages on click.
                mRecyclerView.scrollToPosition(0);
                MessagesEntity chatMessage = new
                        MessagesEntity( mChat_ID,
                            mSelf_User_ID,
                            mMessageEditText.getText().toString(),
                            new Date().getTime());
                sendMessage(chatMessage);
                mMessageEditText.setText("");
            }
        });


        mGoalButton = (Button) findViewById(R.id.goalButton);
        mGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar now = Calendar.getInstance();

                DatePickerDialog dpd = com.borax12.materialdaterangepicker.date.DatePickerDialog.newInstance(
                        ActivityChat.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAutoHighlight(mAutoHighlight);
                dpd.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        mImageButton = (ImageButton) findViewById(R.id.trophyButton);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ActivityGoals.class);
                intent.putExtra("pic_1", mSelf_Picture);
                intent.putExtra("pic_2", mReceipent_Picture);

                startActivity(intent);
            }
        });


    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth,int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String date = "My goal is: From- "+dayOfMonth+"/"+(++monthOfYear)+"/"+year+" To "+dayOfMonthEnd+"/"+(++monthOfYearEnd)+"/"+yearEnd;

        MessagesEntity message = new MessagesEntity(mChat_ID, mSelf_User_ID, date, new Date().getTime());
        sendMessage(message);
    }

    private void sendMessage(MessagesEntity chatMessage) {

        Rapid.getInstance()
                .collection(COLLECTION, MessagesEntity.class)
                .newDocument()
                .mutate(chatMessage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribeDataFromRapid();
    }

    @Override
    protected void onStop() {
        unSubscribeFromRapid();
        super.onStop();
    }

    private void unSubscribeFromRapid() {
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void setUpAdapter(){
        mAdapter = new ChatAdapter(mItems,this);
        mRecyclerView = (android.support.v7.widget.RecyclerView)findViewById(R.id.chatRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void subscribeDataFromRapid() {

        mSubscription = Rapid.getInstance().collection(COLLECTION, MessagesEntity.class)
                .equalTo("chat-id",mChat_ID)
                .orderBy("timestamp")
                .subscribeWithListUpdates(new RapidCallback.CollectionUpdates<MessagesEntity>() {
                    @Override
                    public void onValueChanged(List<RapidDocument<MessagesEntity>> rapidDocuments, ListUpdate listUpdate) {
                        mItems.clear();
                        for(RapidDocument<MessagesEntity> rapidDocument : rapidDocuments) {
                            mItems.add(rapidDocument.getBody());
                        }
                        listUpdate.dispatchUpdateTo(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

}

