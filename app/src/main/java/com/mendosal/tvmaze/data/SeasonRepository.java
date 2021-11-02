package com.mendosal.tvmaze.data;
import androidx.lifecycle.MutableLiveData;

import com.mendosal.tvmaze.retrofit.ShowApiService;
import com.mendosal.tvmaze.retrofit.models.season.SeasonEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeasonRepository {

    private final ShowApiService showApiService;
    //private final ShowDao showDao;
    private List<SeasonEntity> seasonEntities;

    public SeasonRepository(ShowApiService showService) {
        //Local > Room
        /*ShowRoomDatabase showRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                ShowRoomDatabase.class,
                "db_shows"
        ).build();
        showDao = showRoomDatabase.getShowDao();*/
        showApiService = showService;
    }

    public MutableLiveData<List<SeasonEntity>> getShowSeasons(int showId) {
        MutableLiveData<List<SeasonEntity>> seasonsList = new MutableLiveData<>();
        showApiService.getSeasons(showId).enqueue(new Callback<List<SeasonEntity>>() {
            @Override
            public void onResponse(Call<List<SeasonEntity>> call, Response<List<SeasonEntity>> response) {
                if (response.isSuccessful()) {
                    seasonsList.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SeasonEntity>> call, Throwable t) {
                seasonsList.setValue(null);
            }
        });
        return seasonsList;
    }
}
