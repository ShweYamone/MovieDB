package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public interface IMovieInfoModel {
    Observable<MovieInfoModel> getMovieDetailsFromApi(ServiceHelper.ApiService service, int movieId);
}
