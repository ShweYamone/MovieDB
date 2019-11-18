package com.example.moviedb.interactor;

import com.example.moviedb.model.IMovieListModel;
import com.example.moviedb.model.IWatchListModel;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.MovieListModelImpl;
import com.example.moviedb.model.WatchListBody;
import com.example.moviedb.model.WatchListModel;
import com.example.moviedb.model.WatchListModelImpl;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class WatchListInteractor {
    private ServiceHelper.ApiService mService;
    private IWatchListModel watchListModel;

    public WatchListInteractor(ServiceHelper.ApiService service) {
        this.mService = service;
        watchListModel = new WatchListModelImpl();
    }

    public Observable<WatchListModel> addOrRemoveMovieFromWatchList(String sessionId, WatchListBody watchListBody){
        return this.watchListModel.addOrRemoveMovieFromWatchlistFromApi(mService,sessionId,watchListBody);
    }
}
