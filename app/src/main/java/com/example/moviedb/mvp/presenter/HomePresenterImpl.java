package com.example.moviedb.mvp.presenter;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.MovieRateListModel;
import com.example.moviedb.mvp.view.HomeView;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomePresenterImpl extends BasePresenter implements HomePresenter{

    private HomeView homeView = null;
    private MovieInteractor interactor;
    private int ratePages;
    private int watchlistPages;

    public HomePresenterImpl(MovieInteractor interactor){ this.interactor = interactor; }
    @Override
    public void onUIReady(){
        getNowPlayingMovies();
        getPopularMovies();
        getTopRatedMovies();
        getUpComingMovies();
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
        this.interactor.getTopRatedMovieList(1)
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
        this.interactor.getUpcomingMovieList(1)
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
        this.interactor.getPopularMovieList(1)
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



    @Override
    public void getNoOfPages(int accountId, String session_id) {
        //not to get data from cache-------
        ServiceHelper.removeFromCache("account/"+ accountId + "/rated/movies");

        this.interactor.getOwnRatedMovies(accountId, session_id, 1)
                .subscribe(new Observer<MovieRateListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieRateListModel movieRateListModel) {
                        if (movieRateListModel != null) {
                            if(!movieRateListModel.getResults().isEmpty()) {
                                ratePages = movieRateListModel.getTotal_pages();

                               for(int  i = 0; i < ratePages; i++) {

                               }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getNoOfRatedMoviesFromPage(int accountId, String session_id, int page) {

    }

    @Override
    public void getNoOfWatchListMoviesFromPage(int accountId, String session_id, int page) {

    }
}
