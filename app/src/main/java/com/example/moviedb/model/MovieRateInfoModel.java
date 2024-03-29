package com.example.moviedb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

@Entity(tableName = "MovieRateInfoModel")
public class MovieRateInfoModel implements Serializable, Pageable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "accountId")
    private int accountId;

    @ColumnInfo(name = "adult")
    private boolean adult;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster_path")
    private String poster_path;

    @ColumnInfo(name = "rating")
    private float rating;

    @ColumnInfo(name = "overview")
    private String overview;

    public MovieRateInfoModel(int id, int accountId, boolean adult, String release_date, String title, String poster_path, float rating, String overview) {
        this.id = id;
        this.accountId = accountId;
        this.adult = adult;
        this.release_date = release_date;
        this.title = title;
        this.poster_path = poster_path;
        this.rating = rating;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
