package com.example.moviedb.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moviedb.model.RemarkModel;

@Dao
public interface RemarkDAO {

    @Query("Update remarkModel set isRemark=:boolValue where id=:movieId AND accountID=:accountID")
    int changeRemarkValue(boolean boolValue,int movieId,int accountID);

    @Query("Select isRemark from remarkModel where id=:movieId AND accountID=:accountID")
    boolean getRemarkValue(int movieId, int accountID);

    @Insert
    void insertRemark(RemarkModel remarkModel);
}
