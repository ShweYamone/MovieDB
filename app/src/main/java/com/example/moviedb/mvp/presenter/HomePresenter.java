package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.HomeView;

public interface HomePresenter {
    void onUIReady();
    void onAttachView(HomeView homeView);
    void getNowPlayingMovies();
    void getPopularMovies();
    void getTopRatedMovies();
    void getUpComingMovies();

}
