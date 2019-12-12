package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.HomeView;

public interface HomePresenter {
    void onUIReady();
    void onAttachView(HomeView homeView);
    void getNowPlayingMovies();
    void getPopularMovies();
    void getTopRatedMovies();
    void getUpComingMovies();

    void locateDataFromApi();

    void getNoOfRatePages(int accountId, String session_id);
    void addRatedMoviesFromPage(int accountId, String session_id, int page);

    void getNoOfWatchlistPages(int accountId, String session_id);
    void addWatchListMoviesFromPage(int accountId, String session_id, int page);


}
