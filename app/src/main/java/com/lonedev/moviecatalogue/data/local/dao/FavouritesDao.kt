/*
 * Created by Bagus Yogatama on 6/29/19 9:22 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 9:22 PM
 */

package com.lonedev.moviecatalogue.data.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lonedev.moviecatalogue.data.models.Favourites
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavourites(favourites: Favourites): Completable

    @Query("DELETE FROM favourites WHERE favId=:favId")
    fun deleteFavourites(favId: Int): Completable

    @Query("SELECT * FROM movies WHERE movies.id IN (SELECT favId FROM favourites)")
    fun getFavouriteMovies(): Observable<List<MovieResult>>

    @Query("SELECT * FROM tvseries WHERE tvseries.id IN (SELECT favId FROM favourites)")
    fun getFavouriteTVSeries(): Observable<List<TVSeriesResult>>

    @Query("SELECT * FROM movies WHERE movies.id=(SELECT favId FROM favourites WHERE favId=:movieId )")
    fun getFavoritedMovies(movieId: Int): Single<MovieResult>

    @Query("SELECT * FROM tvseries WHERE tvseries.id=(SELECT favId FROM favourites WHERE favId=:tvSeriesId)")
    fun getFavoritedTVSeries(tvSeriesId: Int): Single<TVSeriesResult>


    // Content Resolver
    @Query("SELECT * FROM movies WHERE movies.id IN (SELECT favId FROM favourites)")
    fun getFavouriteMoviesCursor(): Cursor

    @Query("SELECT * FROM tvseries WHERE tvseries.id IN (SELECT favId FROM favourites)")
    fun getFavouriteTVSeriesCursor(): Cursor

    @Query("SELECT * FROM movies WHERE movies.id=(SELECT favId FROM favourites WHERE favId=:movieId )")
    fun getFavoritedMoviesCursor(movieId: Long): Cursor

    @Query("SELECT * FROM tvseries WHERE tvseries.id=(SELECT favId FROM favourites WHERE favId=:tvSeriesId )")
    fun getFavoritedTVCursor(tvSeriesId: Long): Cursor

    @Query("DELETE FROM favourites WHERE favId=:favId")
    fun deleteFavouriteCursor(favId: Long) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavouriteCursor(favourites: Favourites) : Long

}
