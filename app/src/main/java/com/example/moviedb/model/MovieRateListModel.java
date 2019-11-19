package com.example.moviedb.model;

import com.example.moviedb.common.Pageable;

import java.io.Serializable;
import java.util.List;

public class MovieRateListModel implements Serializable, Pageable {
    int page;
    int total_results;
    int total_pages;
    List<MovieRateInfoModel> results;
    int status_code;
    String status_message;


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

    public List<MovieRateInfoModel> getResults() {
        return results;
    }

    public void setResults(List<MovieRateInfoModel> results) {
        this.results = results;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }
}
