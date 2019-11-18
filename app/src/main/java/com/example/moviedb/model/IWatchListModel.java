package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public interface IWatchListModel {
    Observable<WatchListModel> addOrRemoveMovieFromWatchlistFromApi(ServiceHelper.ApiService service, String sessionId,WatchListBody watchListBody);
}
