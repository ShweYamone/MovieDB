package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;


/**
 * Created by kaungkhantsoe on 2019-10-21.
 **/
public class MovieListModelImpl implements IMovieListModel {

    private static final String TAG = "MovieListModelImpl";

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

    @Override
    public Observable<MovieListModel> getTopRatedMoviesFromApi(ServiceHelper.ApiService service, int page){
        return service.getTopRatedMovies(DEVELOPER_KEY,"en_US",page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieListModel> getUpComingMoviesFromApi(ServiceHelper.ApiService service, int page){
        return service.getUpComingMovies(DEVELOPER_KEY,"en_US", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieListModel> getPopularMoviesFromApi(ServiceHelper.ApiService service, int page){
        return service.getPopularMovies(DEVELOPER_KEY, "en_US", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieListModel> getSimilarVideosFromApi(ServiceHelper.ApiService service, int movieId, int page) {
        return service.getSimilarVideos(movieId,DEVELOPER_KEY,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieListModel> getRecommendedVideosFromApi(ServiceHelper.ApiService service, int movieId, int page) {
        return service.getRecommendedVideos(movieId,DEVELOPER_KEY,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MovieListModel> getMyWatchListMoviesFromApi(int accountId, ServiceHelper.ApiService service, String sessionId, int page) {
        return service.getMyWatchListMovies(accountId, DEVELOPER_KEY, "en_US",sessionId, "created_at.desc", page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
