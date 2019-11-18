package com.example.moviedb.model;

public class WatchListBody {
    String media_type;
    int media_id;
    boolean watchlist;

    public WatchListBody(String media_type, int media_id, boolean watchlist) {
        this.media_type = media_type;
        this.media_id = media_id;
        this.watchlist = watchlist;
    }
}
