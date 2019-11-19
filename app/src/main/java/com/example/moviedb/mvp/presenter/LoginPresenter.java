package com.example.moviedb.mvp.presenter;


import com.example.moviedb.mvp.view.LoginView;

/**
 * Created by kaungkhantsoe on 2019-10-18.
 **/
public interface LoginPresenter {

    void onUIReady();
    void onAttachView(LoginView view);

    void onClickLogin(String username, String password);
}
