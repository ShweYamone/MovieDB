package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.mvp.view.ProfileView;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProfilePresenterImpl extends BasePresenter implements ProfilePresenter{

    private ProfileView mView = null;
    private MovieInteractor mInteractor;
    private String mSession_Id;
    private int mAccount_Id;

    public ProfilePresenterImpl(MovieInteractor mInteractor, String mSession_Id, int mAccount_Id) {
        this.mInteractor = mInteractor;
        this.mSession_Id = mSession_Id;
        this.mAccount_Id = mAccount_Id;

    }

    @Override
    public void onUIReady() {
        getWatchListMovies(mSession_Id);
    }

    @Override
    public void onAttachView(ProfileView view) {
        this.mView = view;
    }

    @Override
    public void getWatchListMovies(String mSession_Id) {

        //not to get data from cache-------
        ServiceHelper.removeFromCache("account/"+ mAccount_Id + "/watchlist/movies");
        ServiceHelper.removeFromCache("account/%7Baccount_id%7D/watchlist/movies");

        this.mInteractor.getWatchListMovies(mSession_Id, 1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if(movieListModel != null) {
                            if (movieListModel.getResults().isEmpty()) {
                                mView.showNoMovieInfo();
                            } else {
                                Log.i("Page", "Add page1 movies");
                                mView.showMyWatchList(movieListModel.getResults());

                            }
                        }
                        else {

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
    public void getWatchListByPaging(String mSession_Id, int page) {
        this.mInteractor.getWatchListMovies(mSession_Id, page)
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
                                mView.showMoreWatchList(movieListModel.getResults());

                            }

                        } else {
                            Log.i("Page::", "null");
                            mView.resetPageNumberToDefault();
                            mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                    mView.context().getResources().getString(R.string.please_check_your_internet_connection));
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

}
