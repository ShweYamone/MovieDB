package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.model.MovieRateListModel;
import com.example.moviedb.mvp.view.RateView;
import com.example.moviedb.util.ServiceHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class RatePresenterImpl extends BasePresenter implements RatePresenter{
    private RateView mView = null;
    private MovieInteractor mInteractor;
    private String mSession_Id;
    private int mAccount_Id;
    public InitializeDatabase dbHelper;

    public RatePresenterImpl(MovieInteractor mInteractor, String mSession_Id, int mAccount_Id) {
        this.mInteractor = mInteractor;
        this.mSession_Id = mSession_Id;
        this.mAccount_Id = mAccount_Id;
    }

    @Override
    public void onUIReady() {
        dbHelper = InitializeDatabase.getInstance(this.mView.context());
        getOwnRatedMovies(mAccount_Id, mSession_Id);
    }

    @Override
    public void onAttachView(RateView view) {
        this.mView = view;
    }

    @Override
    public void getOwnRatedMovies(int mAccount_Id, String session_id) {
        //not to get data from cache-------
        ServiceHelper.removeFromCache("account/"+ mAccount_Id + "/rated/movies");

        this.mInteractor.getOwnRatedMovies(mAccount_Id, session_id, 1)
                .subscribe(new Observer<MovieRateListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieRateListModel movieListModel) {

                        if (movieListModel != null) {

                            dbHelper.myRateListDAO().deleteAllFromMovieRateInfoModel();

                            if (movieListModel.getResults().isEmpty()) {

                                mView.showNoRatedMovieInfo();

                            } else {

                                List<MovieRateInfoModel> movieRateList = movieListModel.getResults();

                                for(MovieRateInfoModel movie: movieRateList) {
                                    movie.setAccountId(mAccount_Id);
                                }

                                dbHelper.myRateListDAO().insertAll(movieRateList);

                            }

                        } else {
                            mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                    mView.context().getResources().getString(R.string.please_check_your_internet_connection));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("movie", "error");
                        mView.hideLoading();
                        mView.showDialogMsg(mView.context().getResources().getString(R.string.error_connecting),
                                e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {

                        mView.hideLoading();
                        Log.i("movie", "Complete");
                        mView.showRatedMovieList(dbHelper.myRateListDAO().getMyRatedMovies(mAccount_Id));

                    }
                });
    }

    @Override
    public void getOwnRatedMoviesWithPaging(String session_id, int page) {
        this.mInteractor.getOwnRatedMovies(8776859, session_id, page)
                .subscribe(new Observer<MovieRateListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieRateListModel movieListModel) {

                        if (movieListModel != null) {

                            if (movieListModel.getResults().isEmpty()) {
                                mView.resetPageNumberToDefault();
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
