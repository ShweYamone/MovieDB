package com.example.moviedb.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;

@Entity(tableName = "MyWatchList")
public class MyList implements Pageable {


    @PrimaryKey(autoGenerate = true)
    private int listId;
    @NonNull
    @ColumnInfo(name = "movieId")
    private int movieId;

    public MyList(int movieId) {
        this.movieId = movieId;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }


}
