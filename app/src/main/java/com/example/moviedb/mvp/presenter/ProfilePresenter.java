package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.ProfileView;

public interface ProfilePresenter {
    void onUIReady();
    void onAttachView(ProfileView view);
    void getAccount(String sessionId);
}
