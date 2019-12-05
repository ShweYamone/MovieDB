package com.example.moviedb.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName="remarkModel")
public class RemarkModel {
    @PrimaryKey
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name="accountID")
    int accountID;

    @ColumnInfo(name = "isRemark",defaultValue = "false")
    private boolean isRemark;

    public RemarkModel(int id, int accountID, boolean isRemark) {
        this.id = id;
        this.accountID = accountID;
        this.isRemark = isRemark;
    }

    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRemark() {
        return isRemark;
    }

    public void setRemark(boolean remark) {
        isRemark = remark;
    }
}
