package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviedb.Entity.Movie;
import com.example.moviedb.Entity.MyList;
import com.example.moviedb.Entity.MyRateList;
import com.example.moviedb.model.MovieRateInfoModel;

import java.util.List;

@Dao
public interface MyRateListDAO {

    @Query("SELECT count(*) FROM MyRateList Where movieId=:movieId and accountId=:accountId")
    int getRatedMovieCountbyId(int movieId,int accountId);


    @Query("SELECT movieId FROM MyRateList Where MyRateList.accountId=:accountId")
    List<Integer> getRatedMoviesbyAcoountId(int accountId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MyRateList rateList);

    @Query("Delete from MyRateList where movieId=:movieId and accountId=:accountId")
    void deleteByMovieId(int movieId,int accountId);

    @Query("Update MyRateList set rateValue=:rateValue where movieId=:movieId and accountId=:accountId")
    void updateRateListByMovieId(int movieId,int accountId,float rateValue);

    @Query("SELECT rateValue FROM MyRateList Where movieId=:movieId and accountId=:accountId")
    float getRatedValueByMovieId(int movieId,int accountId);


    //MovieRateInfoModel section................................


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieRateInfoModel> movieRateInfoModels);

    @Query("DELETE FROM MovieRateInfoModel")
    void deleteAllFromMovieRateInfoModel();

    @Query("SELECT * FROM MovieRateInfoModel Where MovieRateInfoModel.accountId=:accountId")
    List<MovieRateInfoModel> getMyRatedMovies(int accountId);


}
