/*
 * Created by Bagus Yogatama on 6/29/19 2:25 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 2:25 PM
 */

package com.lonedev.moviecatalogue.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lonedev.moviecatalogue.data.models.MovieResult
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getMovies(): Single<List<MovieResult>>

    @Query("SELECT * FROM movies WHERE id=:movieId")
    fun getMovie(movieId: Int): Single<MovieResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movieResult: MovieResult)
}