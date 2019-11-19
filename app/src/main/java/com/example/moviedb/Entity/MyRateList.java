package com.example.moviedb.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviedb.common.Pageable;

@Entity(tableName = "MyRateList")
public class MyRateList implements Pageable {
    @PrimaryKey(autoGenerate = true)
    int rateListId;

    @NonNull
    @ColumnInfo(name = "movieId")
    int movieId;

    @ColumnInfo(name = "rateValue")
    float rateValue;

    public MyRateList(int movieId, float rateValue) {
        this.movieId = movieId;
        this.rateValue = rateValue;
    }

    public int getRateListId() {
        return rateListId;
    }

    public void setRateListId(int rateListId) {
        this.rateListId = rateListId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public float getRateValue() {
        return rateValue;
    }

    public void setRateValue(float rateValue) {
        this.rateValue = rateValue;
    }
}
