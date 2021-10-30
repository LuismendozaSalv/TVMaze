package com.mendosal.tvmaze.data;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.mendosal.tvmaze.MyApp;
import com.mendosal.tvmaze.data.local.ShowRoomDatabase;
import com.mendosal.tvmaze.data.local.dao.ShowDao;
import com.mendosal.tvmaze.data.network.NetworkBoundResource;
import com.mendosal.tvmaze.data.network.Resource;
import com.mendosal.tvmaze.retrofit.ApiConstants;
import com.mendosal.tvmaze.retrofit.RequestInterceptor;
import com.mendosal.tvmaze.retrofit.ShowApiService;
import com.mendosal.tvmaze.retrofit.models.show.ShowEntity;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowRepository {

    private final ShowApiService showApiService;
    private final ShowDao showDao;
    private List<ShowEntity> showEntities;
    public ShowRepository() {
        //Local > Room
        ShowRoomDatabase showRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                ShowRoomDatabase.class,
                "db_shows"
        ).build();
        showDao = showRoomDatabase.getShowDao();

        //Request interceptor
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        //Remote Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        showApiService = retrofit.create(ShowApiService.class);
    }

    public MutableLiveData<List<ShowEntity>> getShowEntities() {
        MutableLiveData<List<ShowEntity>> newsData = new MutableLiveData<>();
        showApiService.getShow().enqueue(new Callback<List<ShowEntity>>() {
            @Override
            public void onResponse(Call<List<ShowEntity>> call, Response<List<ShowEntity>> response) {
                if (response.isSuccessful()) {
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ShowEntity>> call, Throwable t) {
                newsData.setValue(null);
            }
        });

        return newsData;
    }

    public LiveData<Resource<List<ShowEntity>>> getShows() {
        return new NetworkBoundResource<List<ShowEntity>, List<ShowEntity>>(){

            @Override
            protected void saveCallResult(@NonNull List<ShowEntity> item) {
                showEntities = item;
                showDao.saveFavoriteShows(item);
            }

            @NonNull
            @Override
            protected LiveData<List<ShowEntity>> loadFromDb() {
                return showDao.loadFavoriteShows();
            }

            @NonNull
            @Override
            protected Call<List<ShowEntity>> createCall() {
                return showApiService.getShow();
            }
        }.getAsLiveData();
    }
}
