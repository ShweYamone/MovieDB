package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;

import java.util.List;

public interface HomeView extends BaseView {
    void showUpComingMovieList(List<MovieInfoModel> movieInfoModelList);
    void showTopRatedMovieList(List<MovieInfoModel> movieInfoModelList);
    void showNowShowingMovieList(List<MovieInfoModel> movieInfoModelList);
    void showPopularMovieList(List<MovieInfoModel> movieInfoModelList);
    void resetPageNumberToDefault();
    void showNoMovieInfo();

}
