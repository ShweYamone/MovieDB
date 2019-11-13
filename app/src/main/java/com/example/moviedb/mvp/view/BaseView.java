package com.example.moviedb.mvp.view;

import android.content.Context;

public interface BaseView {

    Context context();
    void showLoading();
    void hideLoading();
    void showToastMsg(String msg);
    void showDialogMsg(String title,String msg);
}
