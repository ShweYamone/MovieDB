package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.MovieRateListModel;
import com.example.moviedb.mvp.view.RateView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class RatePresenterImpl extends BasePresenter implements RatePresenter{
    private RateView mView = null;
    private MovieInteractor mInteractor;
    private String mSession_Id;

    public RatePresenterImpl(MovieInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }

    public RatePresenterImpl(MovieInteractor mInteractor, String mSession_Id) {
        this.mInteractor = mInteractor;
        this.mSession_Id = mSession_Id;
    }

    @Override
    public void onUIReady() {
        getOwnRatedMovies(mSession_Id);
    }

    @Override
    public void onAttachView(RateView view) {
        this.mView = view;
    }

    @Override
    public void getOwnRatedMovies(String session_id) {

        this.mInteractor.getOwnRatedMovies(session_id, 1)
                .subscribe(new Observer<MovieRateListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieRateListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                mView.showNoRatedMovieInfo();
                            } else {
                                Log.i("Page", "Add page1 movies");
                                mView.showRatedMovieList(movieListModel.getResults());

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
    public void getOwnRatedMoviesWithPaging(String session_id, int page) {
        this.mInteractor.getOwnRatedMovies(session_id, page)
                .subscribe(new Observer<MovieRateListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieRateListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
//                                mView.resetPageNumberToDefault();
                            } else {
                                Log.i("Page", "Add page1 movies");
                                mView.addMoreRatedMoviesToTheList(movieListModel.getResults());

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
}
