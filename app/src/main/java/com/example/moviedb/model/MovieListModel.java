package com.example.moviedb.model;


import com.example.moviedb.common.Pageable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kaungkhantsoe on 2019-10-21.
 **/
public class MovieListModel implements Serializable, Pageable {

    int page;
    int total_results;
    int total_pages;
    List<MovieInfoModel> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<MovieInfoModel> getResults() {
        return results;
    }

    public void setResults(List<MovieInfoModel> results) {
        this.results = results;
    }
}
