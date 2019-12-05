package com.example.moviedb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

@Entity(tableName = "MovieInfoModel")
public class MovieInfoModel implements Serializable, Pageable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "accountId")
    private int accountId;

    @ColumnInfo(name = "adult")
    private boolean adult;

    @ColumnInfo(name = "release_date")
    private String release_date;

    @ColumnInfo(name = "title")
    String title;

    @ColumnInfo(name = "poster_path")
    String poster_path;

    @ColumnInfo(name = "overview")
    String overview;

    int runtime;

    int height;

    @ColumnInfo(name = "isRemarked",defaultValue = "false")
    boolean isRemarked;


    public MovieInfoModel(int id, int accountId, boolean adult, String release_date, String title, String poster_path, String overview) {
        this.id = id;
        this.accountId = accountId;
        this.adult = adult;
        this.release_date = release_date;
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    @Ignore
    public MovieInfoModel(int id, String title, String poster_path, String release_date, boolean adult, String overview, int runtime) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.adult = adult;
        this.overview = overview;
        this.runtime=runtime;
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

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
