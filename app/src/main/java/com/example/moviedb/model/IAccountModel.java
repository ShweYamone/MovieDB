package com.example.moviedb.model;

import com.example.moviedb.util.ServiceHelper;

import io.reactivex.Observable;

public interface IAccountModel {
    Observable<AccountModel> getAccountBySessionIdFromApi(ServiceHelper.ApiService service, String sessionId);
}
