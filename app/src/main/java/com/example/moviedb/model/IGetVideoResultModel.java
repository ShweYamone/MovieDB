package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public interface IGetVideoResultModel {
    Observable<GetVideoResultModel> getVideoFromApi(ServiceHelper.ApiService service, int movieId);
}
