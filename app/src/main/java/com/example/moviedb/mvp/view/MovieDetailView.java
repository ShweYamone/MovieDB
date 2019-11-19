package com.example.moviedb.mvp.view;

import com.example.moviedb.model.MovieInfoModel;

import java.util.List;

public interface MovieDetailView extends BaseView {
    void showMovieDetail(MovieInfoModel movieInfoModel);
    void showSimilarVideos(List<MovieInfoModel> similarVideoListModel);
    void showRecommendedVideos(List<MovieInfoModel> recommendedVideoListModel);
    void hideLabelMoreLikeThis();
    void hideLabelRecommendation();
    void changeMyListIcon();
}