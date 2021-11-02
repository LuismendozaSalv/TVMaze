package com.mendosal.tvmaze.di;

import com.mendosal.tvmaze.data.EpisodeRepository;
import com.mendosal.tvmaze.data.SeasonRepository;
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

    @Singleton
    @Provides
    public SeasonRepository provideSeasonRepository(ShowApiService showApiService) {
        return new SeasonRepository(showApiService);
    }

    @Singleton
    @Provides
    public EpisodeRepository provideEpisodeRepository(ShowApiService showApiService) {
        return new EpisodeRepository(showApiService);
    }
}
