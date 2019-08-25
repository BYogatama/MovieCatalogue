/*
 * Created by Bagus Yogatama on 8/23/19 12:11 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/23/19 12:11 AM
 */

package com.lonedev.moviecatalogue.di

import android.content.Context
import androidx.room.Room
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
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .build()
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