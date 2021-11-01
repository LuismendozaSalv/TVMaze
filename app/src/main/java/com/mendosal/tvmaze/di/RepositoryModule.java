package com.mendosal.tvmaze.di;

import com.mendosal.tvmaze.data.ShowRepository;
import com.mendosal.tvmaze.retrofit.ShowApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Singleton
    @Provides
    public ShowRepository provideShowRepository(ShowApiService showApiService) {
        return new ShowRepository(showApiService);
    }
}
