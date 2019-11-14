package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.SearchView;

public interface SearchPresenter {

    void onUIReady();
    void onAttachView(SearchView view);
    void getMoviesByTitle();
    void getMoviesByTitleWithPaging(int page);
}
