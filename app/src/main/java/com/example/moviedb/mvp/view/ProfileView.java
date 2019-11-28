package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;

import java.util.List;

public interface ProfileView extends BaseView {

    void showMyWatchList(List<MovieInfoModel> movieInfoModelList);
    void showMoreWatchList(List<MovieInfoModel> movieInfoModelList);
    void resetPageNumberToDefault();
    void showNoMovieInfo();

}
