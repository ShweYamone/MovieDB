package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public interface IWatchListModel {
    Observable<WatchListModel> addOrRemoveMovieFromWatchlistFromApi(ServiceHelper.ApiService service,int accountId, String sessionId,WatchListBody watchListBody);
}
