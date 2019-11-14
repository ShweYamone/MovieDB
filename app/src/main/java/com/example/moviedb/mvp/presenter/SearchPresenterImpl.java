package com.example.moviedb.mvp.presenter;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.mvp.view.SearchView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchPresenterImpl extends BasePresenter implements SearchPresenter {
    private SearchView mView = null;
    private MovieInteractor mInteractor;

    public SearchPresenterImpl(MovieInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }

    @Override
    public void onUIReady() {
        getMoviesByTitle();
    }

    @Override
    public void onAttachView(SearchView view) {
        this.mView = view;
    }

    @Override
    public void getMoviesByTitle() {
       // mView.showLoading();

        this.mInteractor.getNowShowingMovieList(1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                mView.showNoMovieInfo();
                            } else {
                                mView.showMovieList(movieListModel.getResults());

                            }

                        } else {
                            mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                    mView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();

                    }
                });

    }

    @Override
    public void getMoviesByTitleWithPaging(int page) {
        mView.showLoading();

        this.mInteractor.getNowShowingMovieList(page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                mView.resetPageNumberToDefault();
                            } else {
                                mView.addMoreMoviesToTheList(movieListModel.getResults());

                            }

                        } else {
                            mView.resetPageNumberToDefault();
                            mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                    mView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
