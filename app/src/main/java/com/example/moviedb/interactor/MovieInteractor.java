package com.example.moviedb.interactor;


import com.example.moviedb.model.IMovieListModel;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.MovieListModelImpl;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class MovieInteractor {

    private ServiceHelper.ApiService mService;
    private IMovieListModel movieListModel;

    public MovieInteractor(ServiceHelper.ApiService service) {
        this.mService = service;
        movieListModel = new MovieListModelImpl();
    }

    public Observable<MovieListModel> getNowShowingMovieList(int page) {
        return this.movieListModel.getNowShowingMoviesFromApi(mService,page);

    }

    public Observable<MovieListModel> getMoviesByTitle(String query, int page) {
        return this.movieListModel.getMoviesByTitleFromApi(mService, query, page);
    }
}
