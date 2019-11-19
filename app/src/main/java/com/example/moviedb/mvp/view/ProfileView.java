package com.example.moviedb.mvp.view;

public interface ProfileView extends BaseView {

    void showUserInfo(String userName);
    void setUserNameandIDToSharePreference(String userName, int userId);
    void showMyWatchList();

}
