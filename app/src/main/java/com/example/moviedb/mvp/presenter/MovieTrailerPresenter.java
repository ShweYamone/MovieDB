package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.MovieTrailerView;

public interface MovieTrailerPresenter {
    void getVideo(int movieId);
    void onUIReady();
    void onAttachView(MovieTrailerView movieTrailerView);
}
