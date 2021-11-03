package com.mendosal.tvmaze.di;

import com.mendosal.tvmaze.repository.EpisodeRepository;
import com.mendosal.tvmaze.repository.SeasonRepository;
import com.mendosal.tvmaze.repository.ShowRepository;
import com.mendosal.tvmaze.repository.local.ShowRoomDatabase;
import com.mendosal.tvmaze.retrofit.ShowApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Singleton
    @Provides
    public ShowRepository provideShowRepository(ShowApiService showApiService, ShowRoomDatabase showRoomDatabase) {
        return new ShowRepository(showApiService, showRoomDatabase);
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
