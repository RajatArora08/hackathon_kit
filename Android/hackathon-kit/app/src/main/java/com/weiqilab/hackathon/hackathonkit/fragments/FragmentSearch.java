package com.weiqilab.hackathon.hackathonkit.fragments;


import android.content.Context;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.weiqilab.hackathon.hackathonkit.activities.ActivityChat;
import com.weiqilab.hackathon.hackathonkit.activities.UserEntity;
import com.weiqilab.hackathon.hackathonkit.extras.SortListener;
import com.weiqilab.hackathon.hackathonkit.logging.L;
import com.weiqilab.hackathon.hackathonkit.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rapid.ListUpdate;
import io.rapid.Rapid;
import io.rapid.RapidCallback;
import io.rapid.RapidCollectionSubscription;
import io.rapid.RapidDocument;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSearch extends Fragment implements SortListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public View mView;
    public CustomUserAdapter mAdapter;
    public RecyclerView mRecyclerView;

    //Rapid.io Hackathon
    private ObservableArrayList<UserEntity> mItems = new ObservableArrayList<>();
    @Nullable
    private RapidCollectionSubscription mSubscription;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void onSortByName(){
        L.t(getActivity(), "sort name search");
    }

    @Override
    public void onSortByDate() {

    }

    @Override
    public void onSortByRating() {

    }

    @Override
    public void onStart() {
        super.onStart();
        subscribeDataFromRapid();
    }

    @Override
    public void onStop() {
        super.onStop();
        unSubscribeFromRapid();
    }

    private void unSubscribeFromRapid() {
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void setUpAdapter(){
        mAdapter = new CustomUserAdapter(mItems,mView.getContext());
        mRecyclerView = (android.support.v7.widget.RecyclerView)mView.findViewById(R.id.userLinearLayout);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        setUpAdapter();
        return mView;
    }

    public void subscribeDataFromRapid() {

        mSubscription = Rapid.getInstance().collection("motiv8-users-mockup", UserEntity.class)
                .subscribeWithListUpdates(new RapidCallback.CollectionUpdates<UserEntity>() {
                    @Override
                    public void onValueChanged(List<RapidDocument<UserEntity>> rapidDocuments, ListUpdate listUpdate) {
                        mItems.clear();
                        for(RapidDocument<UserEntity> rapidDocument : rapidDocuments) {
                            mItems.add(rapidDocument.getBody());
                        }
                        listUpdate.dispatchUpdateTo(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}

class CustomUserAdapter extends RecyclerView.Adapter<CustomUserAdapter.ViewHolder>{

    public ArrayList<UserEntity> mDataSet;
    public LayoutInflater mInflater;
    public Context mContext;

    public CustomUserAdapter(ArrayList<UserEntity> dataSet, Context context){
        this.mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @Override
    public CustomUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // TODO inflate layout here
        View v = mInflater.inflate(R.layout.custom_user_item, parent, false);
        CustomUserAdapter.ViewHolder vH = new CustomUserAdapter.ViewHolder(v);
        return vH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

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
