package com.example.moviedb.interactor;

import com.example.moviedb.model.LoginRequestBody;
import com.example.moviedb.model.ProfileInfoModel;
import com.example.moviedb.model.ProfileInfoModelImpl;
import com.example.moviedb.model.RequestTokenBody;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class LoginInteractor {

    private ServiceHelper.ApiService mService;
    private ProfileInfoModelImpl mProfileInfoModelImpl;

    public LoginInteractor(ServiceHelper.ApiService service) {
        this.mService = service;
        this.mProfileInfoModelImpl = new ProfileInfoModelImpl();
    }

    public Observable<ProfileInfoModel> getRequestToken() {

        return mProfileInfoModelImpl.getRequestTokenFromApi(mService);
    }

    public Observable<ProfileInfoModel> getLoginValidate(LoginRequestBody requestBody) {

        return mProfileInfoModelImpl.getLoginValidteFromApi(mService,requestBody);
    }

    public Observable<ProfileInfoModel> getSession(RequestTokenBody requestTokenBody) {
        return mProfileInfoModelImpl.getSessionByRequestTokenFromApi(mService, requestTokenBody);
    }

}
