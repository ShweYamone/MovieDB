package com.example.moviedb.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

@Entity(tableName = "Movie")
public class Movie implements Pageable, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    private int movieId;

    @ColumnInfo(name = "movieName")
    private String movieName;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    @ColumnInfo(name="isAdult")
    private Boolean isAdult;

    @ColumnInfo(name="duration")
    private String duration;

    @ColumnInfo(name = "overview")
    private String overview;

    public Movie(int movieId, String movieName, String releaseDate, Boolean isAdult, String duration, String overview) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.isAdult = isAdult;
        this.duration = duration;
        this.overview = overview;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
