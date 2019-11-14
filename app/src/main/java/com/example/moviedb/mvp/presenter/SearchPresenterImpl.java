package com.example.moviedb.mvp.presenter;

import android.util.Log;

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
       // getMoviesByTitle();
    }

    @Override
    public void onAttachView(SearchView view) {
        this.mView = view;
    }

    @Override
    public void getMoviesByTitle(String query) {
        this.mInteractor.getMoviesByTitle(query, 1)
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
                                Log.i("Page", "Add page1 movies");
                                mView.showMovieListByQuery(movieListModel.getResults());

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
    public void getMoviesByTitleWithPaging(String query, int page) {
        Log.i("Page", "Add more movies page");
        this.mInteractor.getMoviesByTitle(query, page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);

                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {


                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                Log.i("Page::", "Empty");
                                mView.resetPageNumberToDefault();
                            } else {
                                Log.i("Page::", "Add more movies" + movieListModel.getResults().get(1).getTitle());

                                mView.addMoreMoviesToTheListByQuery(movieListModel.getResults());

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
