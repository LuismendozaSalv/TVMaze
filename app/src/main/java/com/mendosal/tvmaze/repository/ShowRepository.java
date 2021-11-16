package com.mendosal.tvmaze.repository;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mendosal.tvmaze.repository.local.ShowRoomDatabase;
import com.mendosal.tvmaze.repository.local.dao.ShowDao;
import com.mendosal.tvmaze.retrofit.ShowApiService;
import com.mendosal.tvmaze.retrofit.models.show.ScoreShow;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;
import com.mendosal.tvmaze.retrofit.network.NetworkBoundResource;
import com.mendosal.tvmaze.retrofit.network.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowRepository {

    private final ShowApiService showApiService;
    private final ShowDao showDao;
    private List<ShowEntity> showEntities;

    public ShowRepository(ShowApiService showService, ShowRoomDatabase showRoomDatabase) {
        //Local > Room

        showDao = showRoomDatabase.getShowDao();
        showApiService = showService;
    }

    public MutableLiveData<List<ShowEntity>> getShowEntities(int page) {
        MutableLiveData<List<ShowEntity>> showsData = new MutableLiveData<>();
        showApiService.getShowsByPage(page).enqueue(new Callback<List<ShowEntity>>() {
            @Override
            public void onResponse(Call<List<ShowEntity>> call, Response<List<ShowEntity>> response) {
                if (response.isSuccessful()) {
                    showsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ShowEntity>> call, Throwable t) {
                showsData.setValue(null);
            }
        });

        return showsData;
    }

    public MutableLiveData<ShowEntity> getShow(int showId) {
        MutableLiveData<ShowEntity> showData = new MutableLiveData<>();
        showApiService.getShow(showId).enqueue(new Callback<ShowEntity>() {
            @Override
            public void onResponse(Call<ShowEntity> call, Response<ShowEntity> response) {
                if (response.isSuccessful()) {
                    showData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ShowEntity> call, Throwable t) {

            }
        });
        return showData;
    }

    public MutableLiveData<List<ScoreShow>> searchShow(String q) {
        MutableLiveData<List<ScoreShow>> searchedData = new MutableLiveData<>();
        showApiService.searchShow(q).enqueue(new Callback<List<ScoreShow>>() {
            @Override
            public void onResponse(Call<List<ScoreShow>> call, Response<List<ScoreShow>> response) {
                if (response.isSuccessful()) {
                    searchedData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ScoreShow>> call, Throwable t) {
                searchedData.setValue(null);
            }
        });
        return searchedData;
    }

    public LiveData<Resource<ShowEntity>> saveFavoriteShow(int showId) {
        return new NetworkBoundResource<ShowEntity, ShowEntity>(){

            @Override
            protected void saveCallResult(@NonNull ShowEntity item) {
                showDao.saveFavoriteShow(item);
            }

            @NonNull
            @Override
            protected LiveData<ShowEntity> loadFromDb() {
                return showDao.loadFavoriteShow(showId);
            }

            @NonNull
            @Override
            protected Call<ShowEntity> createCall() {
                return showApiService.getShow(showId);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<ShowEntity>>> getFavoritesShows() {
        return new NetworkBoundResource<List<ShowEntity>, ShowEntity>(){

            @Override
            protected void saveCallResult(@NonNull ShowEntity item) {

            }

            @NonNull
            @Override
            protected LiveData<List<ShowEntity>> loadFromDb() {
                return showDao.loadFavoriteShows();
            }

            @NonNull
            @Override
            protected Call<ShowEntity> createCall() {
                return showApiService.getShow(0);
            }
        }.getAsLiveData();
    }
}
