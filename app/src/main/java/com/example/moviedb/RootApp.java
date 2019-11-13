package com.example.moviedb;

import android.app.Application;

import com.example.moviedb.custom_control.AndroidCommonSetup;


/**
 * Created by kaungkhantsoe on 2019-10-18.
 **/
public class RootApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidCommonSetup.getInstance().init(getApplicationContext());

    }
}
