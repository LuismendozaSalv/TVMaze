package com.mendosal.tvmaze.retrofit;

import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.season.SeasonEntity;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ShowApiService {
    
    @GET("shows?page=1")
    Call<List<ShowEntity>> getShowsByPage();

    @GET("shows/{showId}")
    Call<ShowEntity> getShow(@Path("showId") int showId);

    @GET("shows/{showId}/seasons")
    Call<List<SeasonEntity>> getSeasons(@Path("showId") int showId);

    @GET("shows/{showId}/episodes")
    Call<List<EpisodeEntity>> getEpisodes(@Path("showId") int showId);
}
