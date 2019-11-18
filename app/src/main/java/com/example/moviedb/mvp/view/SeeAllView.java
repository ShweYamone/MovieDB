package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;

import java.util.List;

public interface SeeAllView extends BaseView{
    void addMoreNowPlayingMoviesToTheList(List<MovieInfoModel> movieInfoModelLIst);
    void addMoreTopRatedMoviesToTheList(List<MovieInfoModel> movieInfoModelList);
    void addMoreUpcomingMoviesToTheList(List<MovieInfoModel> movieInfoModelList);
    void addMorePopularMoviesToTheList(List<MovieInfoModel> movieInfoModelList);
    void showUpComingMovieList(List<MovieInfoModel> movieInfoModelList);
    void showTopRatedMovieList(List<MovieInfoModel> movieInfoModelList);
    void showNowShowingMovieList(List<MovieInfoModel> movieInfoModelList);
    void showPopularMovieList(List<MovieInfoModel> movieInfoModelList);
    void resetPageNumberToDefault();
    void showNoMovieInfo();
}
