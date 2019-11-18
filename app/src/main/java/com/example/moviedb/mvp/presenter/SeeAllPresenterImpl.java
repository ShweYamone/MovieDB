package com.example.moviedb.mvp.presenter;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.mvp.view.SeeAllView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SeeAllPresenterImpl extends BasePresenter implements SeeAllPresenter {

    private SeeAllView seeAllView = null;
    private MovieInteractor movieInteractor;

    public SeeAllPresenterImpl(MovieInteractor movieInteractor){ this.movieInteractor = movieInteractor; }


    @Override
    public void onTerminate() {
        super.onTerminate();
        this.seeAllView = null;
    }

    @Override
    public void onUIReady(String id) {
        if(id.equals("Upcoming")){
            getUpComingMovies();
        }
        else if(id.equals("Top Rated")){
            getTopRatedMovies();
        }
        else if(id.equals("Popular")){
            getPopularMovies();
        }
        else {
            getNowPlayingMovies();
        }
    }

    @Override
    public void onAttachView(SeeAllView seeAllView) {
        this.seeAllView = seeAllView;
    }

    @Override
    public void getNowPlayingMovies() {
        seeAllView.showLoading();

        this.movieInteractor.getNowShowingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.showNoMovieInfo();
                            } else {
                                seeAllView.showNowShowingMovieList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();

                    }
                });
    }

    @Override
    public void getPopularMovies() {
        seeAllView.showLoading();

        this.movieInteractor.getPopularMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.showNoMovieInfo();
                            } else {
                                seeAllView.showPopularMovieList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();

                    }
                });

    }

    @Override
    public void getTopRatedMovies() {
        seeAllView.showLoading();

        this.movieInteractor.getTopRatedMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.showNoMovieInfo();
                            } else {
                                seeAllView.showTopRatedMovieList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();

                    }
                });

    }

    @Override
    public void getUpComingMovies() {
        seeAllView.showLoading();

        this.movieInteractor.getUpcomingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.showNoMovieInfo();
                            } else {
                                seeAllView.showUpComingMovieList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();

                    }
                });

    }

    @Override
    public void getNowPlayingMoviesByPaging(int page) {
        seeAllView.showLoading();

        this.movieInteractor.getNowShowingMovieList(page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.resetPageNumberToDefault();
                            } else {
                                seeAllView.addMoreNowPlayingMoviesToTheList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.resetPageNumberToDefault();
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();
                    }
                });

    }

    @Override
    public void getUpcomingMoviesByPaging(int page) {
        seeAllView.showLoading();

        this.movieInteractor.getUpcomingMovieList(page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.resetPageNumberToDefault();
                            } else {
                                seeAllView.addMoreUpcomingMoviesToTheList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.resetPageNumberToDefault();
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();
                    }
                });

    }

    @Override
    public void getTopRatedMoviesByPaging(int page) {
        seeAllView.showLoading();

        this.movieInteractor.getTopRatedMovieList(page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.resetPageNumberToDefault();
                            } else {
                                seeAllView.addMoreTopRatedMoviesToTheList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.resetPageNumberToDefault();
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();
                    }
                });

    }

    @Override
    public void getPopularMoviesByPaging(int page) {
        seeAllView.showLoading();

        this.movieInteractor.getPopularMovieList(page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                seeAllView.resetPageNumberToDefault();
                            } else {
                                seeAllView.addMorePopularMoviesToTheList(movieListModel.getResults());

                            }

                        } else {
                            seeAllView.resetPageNumberToDefault();
                            seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                    seeAllView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        seeAllView.hideLoading();
                        seeAllView.showDialogMsg(seeAllView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        seeAllView.hideLoading();
                    }
                });

    }
}
