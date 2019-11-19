package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public interface IMovieRateListModel {

    Observable<MovieRateListModel> getOwnRatedMoviesFromApi(ServiceHelper.ApiService service, String sessionId, int page);
    Observable<MovieRateListModel> rateMovieFromApi(ServiceHelper.ApiService service,int movieId, String sessionId,MovieRateBody movieRateBody);

}
