package com.mendosal.tvmaze.data;
import androidx.lifecycle.MutableLiveData;

import com.mendosal.tvmaze.retrofit.ShowApiService;
import com.mendosal.tvmaze.retrofit.models.episode.EpisodeEntity;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeRepository {

    private final ShowApiService showApiService;
    //private final ShowDao showDao;
    private List<EpisodeEntity> episodeEntities;

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

    public MutableLiveData<List<EpisodeEntity>> getShowEpisodes() {
        MutableLiveData<List<EpisodeEntity>>episodesData = new MutableLiveData<>();


        return episodesData;
    }


}
