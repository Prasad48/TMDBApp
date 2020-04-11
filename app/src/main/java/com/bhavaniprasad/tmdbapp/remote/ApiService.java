package com.bhavaniprasad.tmdbapp.remote;

import com.bhavaniprasad.tmdbapp.Model.ListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/3/discover/movie")
    Call<ListModel> getList(@Query("sort_by") String sort_by,@Query("api_key") String api_key);
}