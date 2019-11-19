package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviedb.Entity.MyList;
import com.example.moviedb.Entity.MyRateList;

@Dao
public interface MyRateListDAO {
    @Query("SELECT count(*) FROM MyRateList Where movieId=:movieId")
    int getRatedMovieCountbyId(int movieId);

    @Insert
    void insert(MyRateList rateList);

    @Delete
    void delete(MyRateList rateList);

    @Query("Delete from MyRateList where movieId=:movieId")
    void deleteByMovieId(int movieId);

    @Query("Update MyRateList set rateValue=:rateValue where movieId=:movieId")
    void updateRateListByMovieId(int movieId,float rateValue);

    @Query("SELECT rateValue FROM MyRateList Where movieId=:movieId")
    float getRatedValueByMovieId(int movieId);
}
