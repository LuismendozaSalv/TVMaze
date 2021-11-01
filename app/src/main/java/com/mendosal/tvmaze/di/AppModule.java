package com.mendosal.tvmaze.di;

import android.app.Application;
import android.content.Context;

import com.mendosal.tvmaze.BaseApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Singleton
    @Provides
    public BaseApp provideApplication(@ApplicationContext Context app) {
        return (BaseApp) app;
    }
}
