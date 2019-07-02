/*
 * Created by Bagus Yogatama on 6/29/19 2:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 2:23 PM
 */

package com.lonedev.moviecatalogue.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lonedev.moviecatalogue.data.local.converters.IntTypeConverter
import com.lonedev.moviecatalogue.data.local.converters.StringTypeConverter
import com.lonedev.moviecatalogue.data.local.dao.FavouritesDao
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import com.lonedev.moviecatalogue.data.models.Favourites
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult

@Database(
    entities = [MovieResult::class, TVSeriesResult::class, Favourites::class],
    version = 1, exportSchema = false
)
@TypeConverters(IntTypeConverter::class, StringTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun tvSeriesDao(): TVSeriesDao
    abstract fun favouritesDao(): FavouritesDao
}