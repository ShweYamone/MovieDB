package com.example.moviedb.mvp.presenter;

import android.widget.Toast;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieDetailInteractor;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.interactor.WatchListInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.WatchListBody;
import com.example.moviedb.model.WatchListModel;
import com.example.moviedb.mvp.view.MovieDetailView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MovieDetailPresenterImpl extends BasePresenter implements MovieDetailPresenter {
    private MovieDetailView movieDetailView;
    private MovieDetailInteractor movieDetailInteractor;
    private MovieInteractor movieInteractor;
    private WatchListInteractor watchListInteractor;

    public MovieDetailPresenterImpl(MovieDetailInteractor movieDetailInteractor,MovieInteractor movieInteractor,WatchListInteractor watchListInteractor ) {
        this.movieDetailInteractor = movieDetailInteractor;
        this.movieInteractor=movieInteractor;
        this.watchListInteractor=watchListInteractor;

    }
    public MovieDetailPresenterImpl(MovieDetailInteractor movieDetailInteractor) {
        this.movieDetailInteractor = movieDetailInteractor;

    }


    @Override
    public void onUIReady(int movieId) {
        showMovieDetailsById(movieId);
        showSimilarVideosById(movieId);
        showRecommendedVideosById(movieId);
    }

    @Override
    public void onAttachView(MovieDetailView view) {
        this.movieDetailView= view;
    }

    @Override
    public void showMovieDetailsById(int movieId) {


        this.movieDetailInteractor.getMovieDetailsById(movieId)
                .subscribe(new Observer<MovieInfoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(MovieInfoModel movieInfoModel) {
                        movieDetailView.showMovieDetail(movieInfoModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        movieDetailView.hideLoading();
                        movieDetailView.showDialogMsg(movieDetailView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        movieDetailView.hideLoading();

                    }
                });
    }

    @Override
    public void showSimilarVideosById(int movieId) {
        movieDetailView.showLoading();

        this.movieInteractor.getSimilarVideosById(movieId,1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {

                            } else {

                                movieDetailView.showSimilarVideos(movieListModel.getResults());

                            }

                        } else {
                            movieDetailView.showDialogMsg(movieDetailView.context().getResources().getString(R.string.error_connecting),
                                    movieDetailView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        movieDetailView.hideLoading();
                        movieDetailView.showDialogMsg(movieDetailView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        movieDetailView.hideLoading();

                    }
                });
    }

    @Override
    public void showRecommendedVideosById(int movieId) {
        movieDetailView.showLoading();

        this.movieInteractor.getRecommendedVideosById(movieId,1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {

                            } else {

                                movieDetailView.showRecommendedVideos(movieListModel.getResults());

                            }

                        } else {
                            movieDetailView.showDialogMsg(movieDetailView.context().getResources().getString(R.string.error_connecting),
                                    movieDetailView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        movieDetailView.hideLoading();
                        movieDetailView.showDialogMsg(movieDetailView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        movieDetailView.hideLoading();

                    }
                });
    }

    @Override
    public void addOrRemoveMovieFromWatchList(String sessionId, WatchListBody watchListBody) {
        movieDetailView.showLoading();
        this.watchListInteractor.addOrRemoveMovieFromWatchList(sessionId,watchListBody)
                .subscribe(new Observer<WatchListModel>() {
            @Override
            public void onSubscribe(Disposable d) {
                addDisposableOberver(d);
            }

            @Override
            public void onNext(WatchListModel watchListModel) {


//                if(watchListModel.getStatus_message()=="Success."){
//                    movieDetailView.showToastMsg("add to watchlist complete");
//                }
//                else{
//                    movieDetailView.showToastMsg("Failed");
//                }
            }

            @Override
            public void onError(Throwable e) {
                movieDetailView.hideLoading();
                movieDetailView.showDialogMsg(movieDetailView.context().getResources().getString(R.string.error_connecting),
                        e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                movieDetailView.hideLoading();
            }
        });
    }


}
