package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateInfoModel;

import java.util.List;

public interface RateView extends BaseView{
    void addMoreRatedMoviesToTheList(List<MovieRateInfoModel> movieInfoModelList);
    void showRatedMovieList(List<MovieRateInfoModel> movieInfoModelList);
    void resetPageNumberToDefault();
    void showNoRatedMovieInfo();
}
