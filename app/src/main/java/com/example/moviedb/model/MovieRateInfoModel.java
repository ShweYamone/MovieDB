package com.example.moviedb.model;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

public class MovieRateInfoModel implements Serializable, Pageable {
    int id;
    String title;
    String poster_path;
    int rating;

    public MovieRateInfoModel(int id, String title, String poster_path, int rating) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
