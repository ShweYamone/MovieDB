package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;

public class MovieRateListModelImpl implements IMovieRateListModel {
    @Override
    public Observable<MovieRateListModel> getOwnRatedMoviesFromApi(ServiceHelper.ApiService service, String sessionId, int page){
        return service.getOwnRatedMovies(DEVELOPER_KEY, sessionId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieRateListModel> rateMovieFromApi(ServiceHelper.ApiService service,int movieId, String sessionId, MovieRateBody movieRateBody) {
        return service.rateMovie(movieId,DEVELOPER_KEY, sessionId, movieRateBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieRateListModel> deleteRatingFromApi(ServiceHelper.ApiService service,int movieId, String sessionId) {
        return service.deleteRating(movieId,DEVELOPER_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
