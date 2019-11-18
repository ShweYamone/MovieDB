package com.example.moviedb.mvp.presenter;


import com.example.moviedb.mvp.view.SeeAllView;

public interface SeeAllPresenter {
    void onUIReady(String id);
    void onAttachView(SeeAllView seeAllView);
    void getNowPlayingMovies();
    void getPopularMovies();
    void getTopRatedMovies();
    void getUpComingMovies();
    void getNowPlayingMoviesByPaging(int page);
    void getUpcomingMoviesByPaging(int page);
    void getTopRatedMoviesByPaging(int page);
    void getPopularMoviesByPaging(int page);
}
