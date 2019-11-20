package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviedb.Entity.MyList;
import com.example.moviedb.Entity.MyRateList;

import java.util.List;

@Dao
public interface MyRateListDAO {
    @Query("SELECT count(*) FROM MyRateList Where movieId=:movieId and accountId=:accountId")
    int getRatedMovieCountbyId(int movieId,int accountId);

    @Query("SELECT CONCAT(movieId, \" \", rateValue) movieId, rateValue FROM MyRateList Where accountId=:accountId")
    List<String> getRatedMoviesbyAcoountId(int accountId);

    @Insert
    void insert(MyRateList rateList);

    @Query("Delete from MyRateList where movieId=:movieId and accountId=:accountId")
    void deleteByMovieId(int movieId,int accountId);

    @Query("Update MyRateList set rateValue=:rateValue where movieId=:movieId and accountId=:accountId")
    void updateRateListByMovieId(int movieId,int accountId,float rateValue);

    @Query("SELECT rateValue FROM MyRateList Where movieId=:movieId and accountId=:accountId")
    float getRatedValueByMovieId(int movieId,int accountId);
}
