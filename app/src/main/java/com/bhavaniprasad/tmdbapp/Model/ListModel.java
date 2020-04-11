package com.bhavaniprasad.tmdbapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListModel {
    @SerializedName("title")
    private String title;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private String vote_average;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("results")
    private List<ListModel> results;

    public String getTitle() {
        return title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public List<ListModel> getResults() {
        return results;
    }
}