package com.mendosal.tvmaze.retrofit;

import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ShowApiService {
    
    @GET("shows?page=1")
    Call<List<ShowEntity>> getShow();


}
