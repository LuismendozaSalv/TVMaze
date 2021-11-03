package com.mendosal.tvmaze.repository;
import androidx.lifecycle.MutableLiveData;

import com.mendosal.tvmaze.retrofit.ShowApiService;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeRepository {

    private final ShowApiService showApiService;
    //private final ShowDao showDao;
    private List<EpisodeEntity> episodeEntities;
    private EpisodeEntity episodeEntity;

    public EpisodeRepository(ShowApiService showService) {
        //Local > Room
        /*ShowRoomDatabase showRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                ShowRoomDatabase.class,
                "db_shows"
        ).build();
        showDao = showRoomDatabase.getShowDao();*/
        showApiService = showService;
    }

    public MutableLiveData<List<EpisodeEntity>> getShowEpisodes(int showId) {
        MutableLiveData<List<EpisodeEntity>> episodesData = new MutableLiveData<>();
        showApiService.getEpisodes(showId).enqueue(new Callback<List<EpisodeEntity>>() {
            @Override
            public void onResponse(Call<List<EpisodeEntity>> call, Response<List<EpisodeEntity>> response) {
                if (response.isSuccessful()) {
                    episodesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<EpisodeEntity>> call, Throwable t) {
                episodesData.setValue(null);
            }
        });
        return episodesData;
    }

    public MutableLiveData<EpisodeEntity> getShowEpisode(int showId, int seasonNumber, int episodeNumber) {
        MutableLiveData<EpisodeEntity> episodeData = new MutableLiveData<>();
        showApiService.getEpisode(showId, seasonNumber, episodeNumber).enqueue(new Callback<EpisodeEntity>() {
            @Override
            public void onResponse(Call<EpisodeEntity> call, Response<EpisodeEntity> response) {
                if (response.isSuccessful()){
                    episodeData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<EpisodeEntity> call, Throwable t) {
                episodeData.setValue(null);
            }
        });
        return episodeData;
    }
}
