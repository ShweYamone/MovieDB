package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviedb.model.MovieInfoModel;


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
    @Query("SELECT * FROM MovieInfoModel Where id=:movieId and accountId=:accountId")
    MovieInfoModel getMovie(int movieId,int accountId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MovieInfoModel movie);

    @Query("SELECT count(*) FROM MovieInfoModel Where id=:movieId and accountId=:accountId")
    int getMoviebyId(int movieId,int accountId);

    @Query("Delete from MovieInfoModel where id=:movieId and accountId=:accountId")
    void deleteById(int movieId,int accountId);

    @Query("Update MovieInfoModel set isRemarked=:boolValue where id=:movieId")
    void changeRemarkValue(boolean boolValue,int movieId);

    @Query("Select isRemarked from MovieInfoModel where id=:movieId")
    boolean getRemarkValue(int movieId);


}
