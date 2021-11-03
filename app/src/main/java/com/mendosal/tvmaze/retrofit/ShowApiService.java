package com.mendosal.tvmaze.retrofit;

import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.season.SeasonEntity;
import com.mendosal.tvmaze.retrofit.models.show.ScoreShow;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ShowApiService {
    
    @GET("shows")
    Call<List<ShowEntity>> getShowsByPage(@Query("page") int page);

    @GET("shows/{showId}")
    Call<ShowEntity> getShow(@Path("showId") int showId);

    @GET("shows/{showId}/seasons")
    Call<List<SeasonEntity>> getSeasons(@Path("showId") int showId);

    @GET("shows/{showId}/episodes")
    Call<List<EpisodeEntity>> getEpisodes(@Path("showId") int showId);

    @GET("shows/{showId}/episodebynumber")
    Call<EpisodeEntity> getEpisode(@Path("showId") int showId,
                                   @Query("season") int season,
                                   @Query("number") int number);

    @GET("search/shows")
    Call<List<ScoreShow>> searchShow(@Query("q") String q);
}
