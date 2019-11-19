package com.example.moviedb.model;

import java.io.Serializable;

/**
 * Created by kaungkhantsoe on 2019-10-21.
 **/
public class RequestTokenBody implements Serializable {

    String request_token;

    public RequestTokenBody(String request_token) {
        this.request_token = request_token;
    }
}
