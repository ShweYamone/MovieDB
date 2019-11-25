package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviedb.Entity.Movie;
import com.example.moviedb.Entity.MyList;

import java.util.List;

@Dao
public interface MyListDAO {

    @Query("SELECT count(*) FROM MyWatchList Where movieId=:movieId and accountId=:accountId")
    int getMoviebyId(int movieId,int accountId);

    @Query("SELECT movieId FROM MyWatchList Where MyWatchList.accountId=:accountId")
    List<Integer> getWatchListMoviesbyAcoountId(int accountId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MyList list);

    @Delete
    void delete(MyList list);

    @Query("Delete from MyWatchList where movieId=:movieId and accountId=:accountId")
    void deleteById(int movieId,int accountId);




}
