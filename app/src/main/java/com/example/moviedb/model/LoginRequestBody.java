package com.example.moviedb.model;

/**
 * Created by kaungkhantsoe on 2019-10-21.
 **/
public class LoginRequestBody {

    String username;
    String password;
    String request_token;

    public LoginRequestBody(String username, String password, String request_token) {
        this.username = username;
        this.password = password;
        this.request_token = request_token;
    }
}
