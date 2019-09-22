/*
 * Created by Bagus Yogatama on 9/22/19 10:53 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 5:13 PM
 */

package com.lonedev.moviecatalogue.db.di

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.lonedev.moviecatalogue.data.local.AppDatabase
import com.lonedev.moviecatalogue.data.local.dao.FavouritesDao
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModuleTest {
    @Singleton
    @Provides
    fun provideRoomDatabase(): AppDatabase {
        val context = ApplicationProvider.getApplicationContext<Context>()
        return Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(database: AppDatabase): MoviesDao = database.moviesDao()

    @Provides
    @Singleton
    fun provideTVSeriesDao(database: AppDatabase): TVSeriesDao = database.tvSeriesDao()

    @Provides
    @Singleton
    fun provideFavouritesDao(database: AppDatabase): FavouritesDao = database.favouritesDao()
}