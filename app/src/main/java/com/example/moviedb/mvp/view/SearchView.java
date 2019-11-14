package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;

import java.util.List;

public interface SearchView extends BaseView{
    void addMoreMoviesToTheListByQuery(List<MovieInfoModel> movieInfoModelList);
    void showMovieListByQuery(List<MovieInfoModel> movieInfoModelList);
    void resetPageNumberToDefault();
    void showNoMovieInfo();
}
