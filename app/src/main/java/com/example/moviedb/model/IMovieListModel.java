package com.example.moviedb.model;


import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

/**
 * Created by kaungkhantsoe on 2019-10-21.
 **/
public interface IMovieListModel {

    Observable<MovieListModel> getNowShowingMoviesFromApi(ServiceHelper.ApiService service, int id);
    Observable<MovieListModel> getTopRatedMoviesFromApi(ServiceHelper.ApiService service,int page);
    Observable<MovieListModel> getPopularMoviesFromApi(ServiceHelper.ApiService service, int page);
    Observable<MovieListModel> getUpComingMoviesFromApi(ServiceHelper.ApiService service, int page);
    Observable<MovieListModel> getMoviesByTitleFromApi(ServiceHelper.ApiService service, String query, int page);
    Observable<MovieListModel> getSimilarVideosFromApi(ServiceHelper.ApiService service,int movieId,int page);
    Observable<MovieListModel> getRecommendedVideosFromApi(ServiceHelper.ApiService service,int movieId,int page);
}
