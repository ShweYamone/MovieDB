package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;

import java.util.List;

public interface SearchView extends BaseView{
    void addMoreMoviesToTheList(List<MovieInfoModel> movieInfoModelList);
    void showMovieList(List<MovieInfoModel> movieInfoModelList);
    void resetPageNumberToDefault();
    void showNoMovieInfo();
}
