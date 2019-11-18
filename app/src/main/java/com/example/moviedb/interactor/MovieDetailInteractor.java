package com.example.moviedb.interactor;

import com.example.moviedb.model.IMovieInfoModel;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieInfoModelImpl;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class MovieDetailInteractor {
    private ServiceHelper.ApiService mService;
    private IMovieInfoModel iMovieInfoModel;


    public MovieDetailInteractor(ServiceHelper.ApiService service) {
        this.mService = service;
        iMovieInfoModel = new MovieInfoModelImpl();
    }

    public Observable<MovieInfoModel> getMovieDetailsById(int movie_id) {
        return this.iMovieInfoModel.getMovieDetailsFromApi(mService,movie_id);

    }
}
