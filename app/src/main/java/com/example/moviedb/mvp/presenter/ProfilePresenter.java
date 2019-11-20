package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.ProfileView;

public interface ProfilePresenter {
    void onUIReady();
    void onAttachView(ProfileView view);

    void getWatchListMovies(String session_id);
    void getWatchListByPaging(String session_id, int page);
}
