package com.mendosal.tvmaze.di;

import android.app.Application;

import androidx.room.Room;

import com.mendosal.tvmaze.presentation.BaseApp;
import com.mendosal.tvmaze.repository.local.ShowRoomDatabase;
import com.mendosal.tvmaze.retrofit.ShowApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Provides
    @Singleton
    public ShowRoomDatabase provideDatabase(BaseApp app) {
        return Room.databaseBuilder(
                app,
                ShowRoomDatabase.class,
                "db_shows"
        ).build();
    }
}
