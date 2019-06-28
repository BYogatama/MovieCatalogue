package com.lonedev.moviecatalogue.di.modules

import com.lonedev.moviecatalogue.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    private var BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofitBuilder() : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(retrofitBuilder: Retrofit.Builder) : Retrofit {
        return retrofitBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}