package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.HomeView;

public interface ChatPresenter {
    void onUIReady();
    void onAttachView(HomeView homeView);

    void getAllMsgs();
}
