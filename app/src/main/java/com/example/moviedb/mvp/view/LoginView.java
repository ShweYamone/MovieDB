package com.example.moviedb.mvp.view;

/**
 * Created by kaungkhantsoe on 2019-10-18.
 **/
public interface LoginView extends BaseView {

    void saveLoginData(String sessionId);
    void onLoginComplete();
    void checkLogin();

    void setUserName_ID(String username, int id);
}
