/*
 * Created by Bagus Yogatama on 6/29/19 9:21 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 9:21 PM
 */

package com.lonedev.moviecatalogue.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lonedev.moviecatalogue.BuildConfig
import com.lonedev.moviecatalogue.data.local.AppDatabase
import com.lonedev.moviecatalogue.data.local.dao.FavouritesDao
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    companion object {
        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
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