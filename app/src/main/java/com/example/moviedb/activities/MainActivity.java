package com.example.moviedb.activities;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moviedb.R;
import com.example.moviedb.common.BaseActivity;
import com.example.moviedb.common.BaseFragment;
import com.example.moviedb.fragment.HomeFragment;
import com.example.moviedb.fragment.MyRatedListFragment;
import com.example.moviedb.fragment.ProfileFragment;
import com.example.moviedb.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    public static Intent getMainActivityIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    public static Intent clearActivitiesAndGetMainActivityIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {
        init(savedInstanceState);

    }
    private void init(Bundle savedInstanceState) {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState != null) { // saved instance state, fragment may exist
            // look up the instance that already exists by tag
            int currentChoice = savedInstanceState.getInt("curChoice");
            Log.i("current", currentChoice + "");
            if(currentChoice == 1)
                loadFragment(new SearchFragment());
            else if(currentChoice == 2)
                loadFragment(new MyRatedListFragment());
            else if(currentChoice == 3)
                loadFragment(new ProfileFragment());
            else
                loadFragment(new HomeFragment());


        } else {
            // only create fragment if they haven't been instantiated already
            loadFragment(new HomeFragment());
        }



    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_search:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_my_list:
                    fragment = new MyRatedListFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;

            }
            return false;

        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }






}
