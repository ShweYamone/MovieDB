package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.DB.InitializeDatabase;
import com.example.moviedb.R;
import com.example.moviedb.interactor.MovieInteractor;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.MovieRateInfoModel;
import com.example.moviedb.model.MovieRateListModel;
import com.example.moviedb.mvp.view.HomeView;
import com.example.moviedb.util.ServiceHelper;
import com.example.moviedb.util.SharePreferenceHelper;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomePresenterImpl extends BasePresenter implements HomePresenter {

    private HomeView homeView = null;
    private MovieInteractor interactor;

    private int ratePages;
    private int watchlistPages;
    private String mSession_Id;
    private int mAccount_Id;
    public InitializeDatabase dbHelper;

    private SharePreferenceHelper mSharePreferenceHelper;

    public HomePresenterImpl(MovieInteractor interactor, String mSession_Id, int mAccount_Id)
    {
        this.interactor = interactor;
        this.mAccount_Id = mAccount_Id;
        this.mSession_Id = mSession_Id;
    }

    @Override
    public void locateDataFromApi() {
        dbHelper = InitializeDatabase.getInstance(this.homeView.context());
        mSharePreferenceHelper = new SharePreferenceHelper(this.homeView.context());
        mAccount_Id = mSharePreferenceHelper.getUserId();
        mSession_Id = mSharePreferenceHelper.getSessionId();
        Log.i("accountInLocateData", mAccount_Id + "");
        //For Rate and Watchlist
        getNoOfRatePages(mAccount_Id, mSession_Id);
        getNoOfWatchlistPages(mAccount_Id, mSession_Id);
    }

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
    public void getNoOfRatePages(int accountId, String session_id) {
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
                                Log.i("AccountIdInRatePages", accountId + "  " + ratePages);
                                for (int i = 1; i < ratePages + 1; i++) {
                                    addRatedMoviesFromPage(accountId, session_id, i);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("!!!", dbHelper.myRateListDAO().getMyRatedMovies(mAccount_Id).size()+"length");
                    }
                });
    }

    @Override
    public void addRatedMoviesFromPage(int accountId, String session_id, int page) {
        //not to get data from cache-------
        ServiceHelper.removeFromCache("account/"+ accountId + "/rated/movies");

        Log.i("page", accountId + "");
        this.interactor.getOwnRatedMovies(accountId, session_id, page)
                .subscribe(new Observer<MovieRateListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieRateListModel movieListModel) {

                        Log.i("page", "page1");
                        if (movieListModel != null) {
                            Log.i("page", "page2");
                            if (!movieListModel.getResults().isEmpty()) {
                                List<MovieRateInfoModel> movieRateList = movieListModel.getResults();
                                Log.i("pageAccount1", movieRateList.size() + "  " );

                                for(MovieRateInfoModel movie: movieRateList) {
                                    movie.setAccountId(accountId);
                                }

                                dbHelper.myRateListDAO().insertAll(movieRateList);
                                Log.i("pageAccount", dbHelper.myRateListDAO().getMyRatedMovies(mAccount_Id).size()+"");
                            }

                        } else {

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
    public void getNoOfWatchlistPages(int accountId, String session_id) {
        //not to get data from cache-------
        ServiceHelper.removeFromCache("account/"+ accountId + "/watchlist/movies");

        this.interactor.getWatchListMovies(accountId, session_id, 1)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if (movieListModel != null) {
                            if(!movieListModel.getResults().isEmpty()) {
                                watchlistPages = movieListModel.getTotal_pages();
                                for (int i = 1; i < watchlistPages + 1; i++) {
                                    addWatchListMoviesFromPage(accountId, session_id, i);
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("!!!", dbHelper.myListDAO().getMyWatchlistMovies(mAccount_Id).size() + "lengthforlist");
                    }
                });
    }

    @Override
    public void addWatchListMoviesFromPage(int accountId, String session_id, int page) {
        //not to get data from cache-------
        ServiceHelper.removeFromCache("account/"+ accountId + "/watchlist/movies");

        this.interactor.getWatchListMovies(accountId, session_id, page)
                .subscribe(new Observer<MovieListModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposableOberver(d);
                    }

                    @Override
                    public void onNext(MovieListModel movieListModel) {
                        if (movieListModel != null) {

                            if (!movieListModel.getResults().isEmpty()) {
                                List<MovieInfoModel> movieWatchList = movieListModel.getResults();


                                for(MovieInfoModel movie: movieWatchList) {
                                    movie.setAccountId(accountId);
                                }

                                dbHelper.myListDAO().insertAll(movieWatchList);
                                Log.i("page" , dbHelper.myListDAO().getMyWatchlistMovies(mAccount_Id).size()+"");
                            }

                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("movie", "error");

                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }
}
