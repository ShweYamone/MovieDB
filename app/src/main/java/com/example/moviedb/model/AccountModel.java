package com.example.moviedb.model;

import com.example.moviedb.common.Pageable;

public class AccountModel implements Pageable {
    int id;
    String username;

    public AccountModel(int id, String name) {
        this.id = id;
        this.username = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }
}
