package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviedb.Entity.MyList;

@Dao
public interface MyListDAO {

    @Query("SELECT count(*) FROM MyWatchList Where movieId=:movieId and accountId=:accountId")
    int getMoviebyId(int movieId,int accountId);

    @Insert
    void insert(MyList list);

    @Delete
    void delete(MyList list);

    @Query("Delete from MyWatchList where movieId=:movieId and accountId=:accountId")
    void deleteById(int movieId,int accountId);




}
