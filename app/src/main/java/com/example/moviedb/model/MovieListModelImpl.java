package com.example.moviedb.model;


import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;


/**
 * Created by kaungkhantsoe on 2019-10-21.
 **/
public class MovieListModelImpl implements IMovieListModel {
    @Override
    public Observable<MovieListModel> getNowShowingMoviesFromApi(ServiceHelper.ApiService service, int page) {

        return service.getNowShowingMovies(DEVELOPER_KEY, "en-US", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieListModel> getMoviesByTitleFromApi(ServiceHelper.ApiService service, String query, int page) {

        return service.getMoviesByTitle(DEVELOPER_KEY, "en-US",query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
