package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateInfoModel;

import java.util.List;

@Dao
public interface MyListDAO {


    //MovieInfoModel Section................................

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieInfoModel> movieWatchListModel);

    @Query("SELECT * FROM MovieInfoModel Where MovieInfoModel.accountId=:accountId")
    List<MovieInfoModel> getMyWatchlistMovies(int accountId);

    @Query("DELETE FROM MovieInfoModel")
    void deleteAllFromMovieRateInfoModel();


}
