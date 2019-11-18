package com.example.moviedb.interactor;

import com.example.moviedb.model.GetVideoResultModel;
import com.example.moviedb.model.GetVideoResultModelImpl;
import com.example.moviedb.model.IGetVideoResultModel;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class GetVideoResultInteractor {
    private ServiceHelper.ApiService mService;
    private IGetVideoResultModel getVideoResultModel;

    public GetVideoResultInteractor(ServiceHelper.ApiService mService) {
        this.mService = mService;
        getVideoResultModel=new GetVideoResultModelImpl();
    }

    public Observable<GetVideoResultModel> getVideoById(int movie_id){
        return this.getVideoResultModel.getVideoFromApi(mService,movie_id);
    }
}
