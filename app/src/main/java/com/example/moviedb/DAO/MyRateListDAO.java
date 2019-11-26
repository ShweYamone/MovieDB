package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviedb.model.MovieRateInfoModel;

import java.util.List;

@Dao
public interface MyRateListDAO {

    //MovieRateInfoModel section................................


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieRateInfoModel> movieRateInfoModels);

    @Query("DELETE FROM MovieRateInfoModel")
    void deleteAllFromMovieRateInfoModel();

    @Query("SELECT * FROM MovieRateInfoModel Where MovieRateInfoModel.accountId=:accountId")
    List<MovieRateInfoModel> getMyRatedMovies(int accountId);


}
