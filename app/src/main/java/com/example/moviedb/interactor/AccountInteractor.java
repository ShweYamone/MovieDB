package com.example.moviedb.interactor;

import com.example.moviedb.model.AccountModel;
import com.example.moviedb.model.AccountModelImpl;
import com.example.moviedb.model.IAccountModel;
import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public class AccountInteractor {
    private ServiceHelper.ApiService mService;
    private IAccountModel accountModel;

    public AccountInteractor(ServiceHelper.ApiService service) {
        this.mService = service;
        accountModel = new AccountModelImpl();
    }

    public Observable<AccountModel> getAccoount(String sessionId) {
        return this.accountModel.getAccountBySessionIdFromApi(mService, sessionId);
    }


}
