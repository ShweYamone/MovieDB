package com.example.moviedb.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.moviedb.DAO.MovieDAO;
import com.example.moviedb.DAO.MyListDAO;
import com.example.moviedb.DAO.MyRateListDAO;
import com.example.moviedb.Entity.Movie;
import com.example.moviedb.Entity.MyList;
import com.example.moviedb.Entity.MyRateList;
import com.example.moviedb.model.MovieInfoModel;
import com.example.moviedb.model.MovieRateInfoModel;

@Database(entities = {MyList.class, MyRateList.class, Movie.class, MovieRateInfoModel.class, MovieInfoModel.class}, version = 1,exportSchema = false)
public abstract class InitializeDatabase extends RoomDatabase {
    private static InitializeDatabase INSTANCE;

    public abstract MyListDAO myListDAO();
    public abstract MyRateListDAO myRateListDAO();
    public abstract MovieDAO movieDAO();



    public static InitializeDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static InitializeDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                InitializeDatabase.class,
                "my-database")
                .allowMainThreadQueries()
                .build();
    }


}
