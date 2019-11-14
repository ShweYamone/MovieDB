package com.example.moviedb.mvp.presenter;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.mvp.view.HomeView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomePresenterImpl extends BasePresenter implements HomePresenter {

    private HomeView homeView = null;
    private MovieInteractor interactor;

    public HomePresenterImpl(MovieInteractor interactor){ this.interactor = interactor; }
    @Override
    public void onUIReady(){

    }

    @Override
    public void onAttachView(HomeView homeView){
        this.homeView = homeView;
    }

    @Override
    public void getNowPlayingMovies(){
        this.interactor.getNowShowingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                homeView.showNoMovieInfo();
                            } else {
                                homeView.showNowShowingMovieList(movieListModel.getResults());

                            }

                        } else {
                            homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                    homeView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.hideLoading();
                        homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        homeView.hideLoading();
                    }
                });

    }

    @Override
    public void getTopRatedMovies(){
        this.interactor.getNowShowingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                homeView.showNoMovieInfo();
                            } else {
                                homeView.showTopRatedMovieList(movieListModel.getResults());

                            }

                        } else {
                            homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                    homeView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.hideLoading();
                        homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        homeView.hideLoading();
                    }
                });

    }

    @Override
    public void getUpComingMovies(){
        this.interactor.getNowShowingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                homeView.showNoMovieInfo();
                            } else {
                                homeView.showUpComingMovieList(movieListModel.getResults());

                            }

                        } else {
                            homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                    homeView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.hideLoading();
                        homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        homeView.hideLoading();
                    }
                });

    }

    @Override
    public void getPopularMovies(){
        this.interactor.getNowShowingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                homeView.showNoMovieInfo();
                            } else {
                                homeView.showPopularMovieList(movieListModel.getResults());

                            }

                        } else {
                            homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                    homeView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        homeView.hideLoading();
                        homeView.showDialogMsg(homeView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        homeView.hideLoading();
                    }
                });

    }
}
