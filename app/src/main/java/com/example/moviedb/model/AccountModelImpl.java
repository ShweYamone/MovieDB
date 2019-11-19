package com.example.moviedb.model;

import android.util.Log;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.moviedb.util.AppConstant.DEVELOPER_KEY;

public class AccountModelImpl implements IAccountModel {
    @Override
    public Observable<AccountModel> getAccountBySessionIdFromApi(ServiceHelper.ApiService service, String sessionId) {
        Log.i("!!!sessionid", sessionId);
        return service.getAccount(DEVELOPER_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
