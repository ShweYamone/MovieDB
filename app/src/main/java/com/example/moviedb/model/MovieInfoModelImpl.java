package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;

public class MovieInfoModelImpl implements IMovieInfoModel{
    @Override
    public Observable<MovieInfoModel> getMovieDetailsFromApi(ServiceHelper.ApiService service, int movieId) {
        return service.getDetails(movieId,DEVELOPER_KEY )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
