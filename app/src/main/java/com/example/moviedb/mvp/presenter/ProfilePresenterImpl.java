package com.example.moviedb.mvp.presenter;

import android.util.Log;

import com.example.moviedb.interactor.AccountInteractor;
import com.example.moviedb.model.AccountModel;
import com.example.moviedb.mvp.view.ProfileView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProfilePresenterImpl implements ProfilePresenter{

    private ProfileView mView = null;
    private AccountInteractor mInteractor;
    private String mSession_Id;

    public ProfilePresenterImpl(AccountInteractor mInteractor) {
        this.mInteractor = mInteractor;
    }

    public ProfilePresenterImpl(AccountInteractor mInteractor, String mSession_Id) {
        this.mInteractor = mInteractor;
        this.mSession_Id = mSession_Id;
    }

    @Override
    public void onUIReady() {
   //     getAccount(mSession_Id);
    }

    @Override
    public void onAttachView(ProfileView view) {
        this.mView = view;
    }

    @Override
    public void getAccount(String sessionId) {
        this.mInteractor.getAccoount(sessionId)
                .subscribe(new Observer<AccountModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AccountModel accountModel) {
                        Log.i("!!!", "next");

                        if(accountModel != null) {
                            mView.setUserNameandIDToSharePreference(accountModel.getName(),
                                                                    accountModel.getId());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("!!!", "error");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
