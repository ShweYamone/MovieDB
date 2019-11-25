package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.RateView;

public interface RatePresenter {

    void onUIReady();
    void onAttachView(RateView view);

    void getOwnRatedMovies(int accountId, String session_id);
    void getOwnRatedMoviesWithPaging(String session_id, int page);
}
