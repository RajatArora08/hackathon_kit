package com.weiqilab.hackathon.hackathonkit.activities;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.weiqilab.hackathon.hackathonkit.R;
import com.weiqilab.hackathon.hackathonkit.entities.UserEntity;
import com.weiqilab.hackathon.hackathonkit.fragments.FragmentDrawer;
import com.weiqilab.hackathon.hackathonkit.fragments.FragmentSearch;
import com.weiqilab.hackathon.hackathonkit.fragments.FragmentUpcoming;
import com.weiqilab.hackathon.hackathonkit.logging.L;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import android.databinding.ObservableArrayList;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

//import org.alfonz.adapter.SimpleDataBoundRecyclerAdapter;

import io.rapid.RapidCollectionSubscription;


public class ActivityMain extends AppCompatActivity implements MaterialTabListener {

    //int representing our 0th tab corresponding to the Fragment where search results are dispalyed
    public static final int TAB_SEARCH_RESULTS = 0;
    //int corresponding to our 1st tab corresponding to the Fragment where box office hits are dispalyed
    public static final int TAB_HITS = 1;
    //int corresponding to our 2nd tab corresponding to the Fragment where upcoming movies are displayed
    public static final int TAB_UPCOMING = 2;
    //int corresponding to the number of tabs in our Activity
    public static final int TAB_COUNT = 2;
    //int corresponding to the id of our JobSchedulerService
    private static final int JOB_ID = 100;
    //tag associated with the FAB menu button that sorts by name
    private static final String TAG_SORT_NAME = "sortName";
    //tag associated with the FAB menu button that sorts by date
    private static final String TAG_SORT_DATE = "sortDate";
    //tag associated with the FAB menu button that sorts by ratings
    private static final String TAG_SORT_RATINGS = "sortRatings";
    //Run the JobSchedulerService every 2 minutes
    private static final long POLL_FREQUENCY = 28800000;
    private Toolbar mToolbar;
    //a layout grouping the toolbar and the tabs together
    private ViewGroup mContainerToolbar;
    private MaterialTabHost mTabHost;
    private ViewPager mPager;
    private ViewPagerAdapter mAdapter;
    private FragmentDrawer mDrawerFragment;


    //Rapid.io Hackathon
    private ObservableArrayList<UserEntity> mItems = new ObservableArrayList<>();
    @Nullable private RapidCollectionSubscription mSubscription;
    private RecyclerView.Adapter mRapidAdapter;
    public static final String SELF_USER_ID="SELF_USER_ID";
    public static final String SELF_PICTURE="SELF_PICTURE";
    public static final String RECEIPENT_USER_ID="RECEIPENT_USER_ID";
    public static final String RECEIPENT_PICTURE="RECEIPENT_PICTURE";
    public static final String CHAT_ID = "CHAT_ID";
    public static final String SELF_USER_NAME = "SELF_USER_NAME";
    public static final String RECEIPENT_USER_NAME = "RECEIPENT_USER_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs();
        setupDrawer();
        //animate the Toolbar when it comes into the picture
//        AnimationUtils.animateToolbarDroppingDown(mContainerToolbar);
    }

    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mContainerToolbar = (ViewGroup) findViewById(R.id.container_app_bar);
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setup the NavigationDrawer
        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
    }


    public void onDrawerItemClicked(int index) {
        mPager.setCurrentItem(index);
    }

    public View getContainerToolbar() {
        return mContainerToolbar;
    }

    private void setupTabs() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        //when the page changes in the ViewPager, update the Tabs accordingly
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTabHost.setSelectedNavigationItem(position);

            }
        });
        //Add all the Tabs to the TabHost
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mAdapter.getIcon(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present. 
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will 
        // automatically handle clicks on the Home/Up button, so long 
        // as you specify a parent activity in AndroidManifest.xml. 
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement 
        if (id == R.id.action_settings) {
            L.m("Settings selected");
            //Start new activity here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTabSelected(MaterialTab materialTab) {
        //when a Tab is selected, update the ViewPager to reflect the changes
        mPager.setCurrentItem(materialTab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {
    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        int icons[] = {R.drawable.ic_action_search,
                R.drawable.ic_action_trending};

        FragmentManager fragmentManager;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public Fragment getItem(int num) {
            Fragment fragment = null;
            switch (num) {
                case TAB_SEARCH_RESULTS:
                    fragment = FragmentSearch.newInstance("", "");
                    break;
                case TAB_HITS:
                    fragment = FragmentUpcoming.newInstance("", "");
                    break;
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }

    }
} 