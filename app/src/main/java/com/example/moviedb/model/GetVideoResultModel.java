package com.example.moviedb.model;

import java.util.ArrayList;

public class GetVideoResultModel {
    int id;
    ArrayList<MovieTrailerModel> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<MovieTrailerModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieTrailerModel> results) {
        this.results = results;
    }
}
