package com.example.moviedb.activities;

import android.os.Bundle;
import android.util.Log;

import com.example.moviedb.common.BaseActivity;


/**
 * Created by kaungkhantsoe on 2019-10-17.
 **/
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected void setUpContents(Bundle savedInstanceState) {

        Log.e("hello","hi hi");
         Log.i("Test Sourcetree", "Sourcetree");

    }
}
