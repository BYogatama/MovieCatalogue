/*
 * Created by Bagus Yogatama on 7/20/19 8:22 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 1:19 AM
 */

package com.lonedev.favouriteapp.di.modules

import com.lonedev.favouriteapp.BuildConfig
import com.lonedev.favouriteapp.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(retrofitBuilder: Retrofit.Builder): Retrofit {
        return retrofitBuilder.build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }
}