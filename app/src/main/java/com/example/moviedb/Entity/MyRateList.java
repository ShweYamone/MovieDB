package com.example.moviedb.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviedb.common.Pageable;

@Entity(tableName = "MyRateList")
public class MyRateList implements Pageable {
    @PrimaryKey(autoGenerate = true)
    private int rateListId;

    @NonNull
    @ColumnInfo(name = "movieId")
    private int movieId;

    @ColumnInfo(name = "rateValue")
    private float rateValue;

    @ColumnInfo(name = "accountId")
    private int accountId;

    public MyRateList(int movieId, float rateValue, int accountId) {
        this.movieId = movieId;
        this.rateValue = rateValue;
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
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
