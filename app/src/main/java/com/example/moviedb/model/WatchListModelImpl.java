package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;

public class WatchListModelImpl implements IWatchListModel{


    @Override
    public Observable<WatchListModel> addOrRemoveMovieFromWatchlistFromApi(ServiceHelper.ApiService service, int accountId,String sessionId, WatchListBody watchListBody) {
        return service.addOrRemoveMovieFromWatchList(accountId,DEVELOPER_KEY,sessionId,watchListBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
