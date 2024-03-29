package com.example.moviedb.mvp.presenter;

import com.example.moviedb.model.MovieRateBody;
import com.example.moviedb.model.WatchListBody;
import com.example.moviedb.mvp.view.MovieDetailView;

public interface MovieDetailPresenter {
    void onUIReady(int movieId);
    void onAttachView(MovieDetailView view);
    void showMovieDetailsById(int movieId);
    void showSimilarVideosById(int movieId);
    void showRecommendedVideosById(int movieId);
    void addOrRemoveMovieFromWatchList(int accountId,String sessionId, WatchListBody watchListBody);
    void rateMovie(int movieId,String sessionId, MovieRateBody movieRateBody);
    void deleteRating(int movieId,String sessionId);
}
