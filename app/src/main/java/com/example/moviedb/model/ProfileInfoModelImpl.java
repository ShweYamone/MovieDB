package com.example.moviedb.model;


import android.util.Log;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;

/**
 * Created by kaungkhantsoe on 2019-10-19.
 **/
public class ProfileInfoModelImpl implements IProfileModel {

    @Override
    public Observable<ProfileInfoModel> getRequestTokenFromApi(ServiceHelper.ApiService service) {
      //  Log.i("!!!!", DEVELOPER_KEY);

        return service.getRequestToken(DEVELOPER_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ProfileInfoModel> getLoginValidteFromApi(ServiceHelper.ApiService service, LoginRequestBody requestBody) {
        return service.getLoginValidate(DEVELOPER_KEY,requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ProfileInfoModel> getSessionByRequestTokenFromApi(ServiceHelper.ApiService service, RequestTokenBody requestTokenBody) {
        return service.getSession(DEVELOPER_KEY,requestTokenBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
