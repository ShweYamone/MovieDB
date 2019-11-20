package com.example.moviedb.interactor;


import com.example.moviedb.model.IMovieListModel;
import com.example.moviedb.model.IMovieRateListModel;
import com.example.moviedb.model.MovieListModel;
import com.example.moviedb.model.MovieListModelImpl;
import com.example.moviedb.model.MovieRateBody;
import com.example.moviedb.model.MovieRateListModel;
import com.example.moviedb.model.MovieRateListModelImpl;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class MovieInteractor {

    private ServiceHelper.ApiService mService;
    private IMovieListModel movieListModel;
    private IMovieRateListModel movieRateListModel;

    public MovieInteractor(ServiceHelper.ApiService service) {
        this.mService = service;
        movieListModel = new MovieListModelImpl();
        movieRateListModel = new MovieRateListModelImpl();
    }

    public Observable<MovieListModel> getNowShowingMovieList(int page) {
        return this.movieListModel.getNowShowingMoviesFromApi(mService,page);

    }

    //Get Top Rated Movies
    public Observable<MovieListModel> getTopRatedMovieList(int page){
        return this.movieListModel.getTopRatedMoviesFromApi(mService, page);
    }

    public Observable<MovieListModel> getUpcomingMovieList(int page){
        return this.movieListModel.getUpComingMoviesFromApi(mService, page);
    }

    public Observable<MovieListModel> getPopularMovieList(int page){
        return this.movieListModel.getPopularMoviesFromApi(mService,page);
    }

    //SearchMovies
    public Observable<MovieListModel> getMoviesByTitle(String query, int page) {
        return this.movieListModel.getMoviesByTitleFromApi(mService, query, page);
    }

    //OwnRatedMovies
    public Observable<MovieRateListModel> getOwnRatedMovies(String sessionId, int page) {
        return this.movieRateListModel.getOwnRatedMoviesFromApi(mService, sessionId, page);
    }


    public Observable<MovieListModel> getSimilarVideosById(int movieId,int page){
        return this.movieListModel.getSimilarVideosFromApi(mService,movieId,page);
    }

    public Observable<MovieListModel> getRecommendedVideosById(int movieId,int page){
        return this.movieListModel.getRecommendedVideosFromApi(mService,movieId,page);
    }


    public Observable<MovieRateListModel> rateMovie(int movieId,String sessionId, MovieRateBody movieRateBody) {
        return this.movieRateListModel.rateMovieFromApi(mService,movieId, sessionId,movieRateBody);
    }


    public Observable<MovieRateListModel> deleteRating(int movieId,String sessionId) {
        return this.movieRateListModel.deleteRatingFromApi(mService,movieId, sessionId);
    }

    public Observable<MovieListModel> getWatchListMovies(String sessionId,int page){
        return this.movieListModel.getMyWatchListMoviesFromApi(mService,sessionId,page);
    }
 
}
