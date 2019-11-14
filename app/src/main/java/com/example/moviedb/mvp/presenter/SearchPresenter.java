package com.example.moviedb.mvp.presenter;

import com.example.moviedb.mvp.view.SearchView;

public interface SearchPresenter {

    void onUIReady();
    void onAttachView(SearchView view);
    void getMoviesByTitle(String query);
    void getMoviesByTitleWithPaging(String query, int page);
}
