package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviedb.Entity.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("Update Movie set movieName=:movieName,releaseDate=:releaseDate,isAdult=:isAdult,duration=:duration, overview=:overview  where movieId=:movieId ")
    void updateMovieByMovieId(int movieId,String movieName,String releaseDate,boolean isAdult,String duration,String overview);

    @Query("SELECT count(*) FROM Movie Where movieId=:movieId ")
    int getMoviebyId(int movieId );

    @Query("SELECT * from Movie where movieId=:movieId")
    Movie getMovieInfobyId(int movieId);

    @Query("SELECT * from Movie where movieId in (:ids)")
    List<Movie> getMoviesByMoviesId(List<Integer> ids);

}
