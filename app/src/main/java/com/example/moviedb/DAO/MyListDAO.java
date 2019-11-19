package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviedb.Entity.MyList;

@Dao
public interface MyListDAO {

    @Query("SELECT count(*) FROM MyWatchList Where movieId=:movieId")
    int getMoviebyId(int movieId);

    @Insert
    void insert(MyList list);

    @Delete
    void delete(MyList list);

    @Query("Delete from MyWatchList where movieId=:movieId")
    void deleteById(int movieId);




}
