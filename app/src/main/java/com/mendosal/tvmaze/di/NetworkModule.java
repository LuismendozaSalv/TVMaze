package com.mendosal.tvmaze.di;

import com.mendosal.tvmaze.retrofit.ApiConstants;
import com.mendosal.tvmaze.retrofit.RequestInterceptor;
import com.mendosal.tvmaze.retrofit.ShowApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        //Request interceptor
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ShowApiService provideShowApiService(Retrofit retrofit) {
        return retrofit.create(ShowApiService.class);
    }
}
