/*
 * Created by Bagus Yogatama on 7/20/19 8:29 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 8:29 PM
 */

package com.lonedev.favouriteapp.data.repositories

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import com.lonedev.favouriteapp.data.models.Favourites
import com.lonedev.favouriteapp.data.models.MovieResult
import com.lonedev.favouriteapp.data.models.TVSeriesResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(private val contentResolver: ContentResolver) {

    companion object {
        private const val FAVOURITE_MOVIE_AUTH = "com.lonedev.moviecatalogue.provider.movies"
        private const val FAVOURITE_TV_AUTH = "com.lonedev.moviecatalogue.provider.tvs"

        val FAVOURITE_MOVIE_URI: Uri = Uri.parse(
            "content://$FAVOURITE_MOVIE_AUTH/favourites"
        )

        val FAVOURITE_TV_URI: Uri = Uri.parse(
            "content://$FAVOURITE_TV_AUTH/favourites"
        )
    }

    fun saveFavourites(movieId: Int): Completable {
        val favourites = ContentValues().apply {
            put("id", 0)
            put("favId", movieId)
            put("type", Favourites.Type.MOVIES)
        }

        val cursor = contentResolver.insert(FAVOURITE_MOVIE_URI, favourites)

        return Completable.fromSingle(Single.just(cursor))
    }

    fun deleteMovieFavourites(favoriteId: Int): Completable {
        val cursor = contentResolver.delete(
            ContentUris.withAppendedId(FAVOURITE_MOVIE_URI, favoriteId.toLong()),
            null, null
        )

        return Completable.fromSingle(Single.just(cursor))
    }

    fun deleteTVFavourites(favoriteId: Int): Completable {
        val cursor = contentResolver.delete(
            ContentUris.withAppendedId(FAVOURITE_TV_URI, favoriteId.toLong()),
            null, null
        )

        return Completable.fromSingle(Single.just(cursor))
    }

    fun getFavoritedMovie(movieId: Int): Single<MovieResult> {
        val cursor = contentResolver.query(
            ContentUris.withAppendedId(FAVOURITE_MOVIE_URI, movieId.toLong()),
            null, null, null, null, null
        )
        cursor?.moveToFirst()
        val result = MovieResult(cursor!!)
        cursor.close()
        return Single.just(result)
    }

    fun getFavoritedTVSeries(tvId: Int): Single<TVSeriesResult> {
        val cursor = contentResolver.query(
            ContentUris.withAppendedId(FAVOURITE_TV_URI, tvId.toLong()),
            null, null, null, null, null
        )
        cursor?.moveToFirst()
        val result = TVSeriesResult(cursor!!)
        cursor.close()
        return Single.just(result)
    }

    fun getFavoriteMovies(): Observable<List<MovieResult>> {
        val cursor = contentResolver.query(
            FAVOURITE_MOVIE_URI,
            null, null, null, null, null
        )

        val listOfTVs: MutableList<MovieResult> = ArrayList()

        cursor?.moveToFirst()
        while (!cursor!!.isAfterLast) {
            listOfTVs.add(MovieResult(cursor))
            cursor.moveToNext()
        }
        cursor.close()

        return Observable.fromArray(listOfTVs)

    }

    fun getFavoriteTVSeries(): Observable<List<TVSeriesResult>> {
        val cursor = contentResolver.query(
            FAVOURITE_TV_URI,
            null, null, null, null, null
        )

        val listOfTVs: MutableList<TVSeriesResult> = ArrayList()

        cursor?.moveToFirst()
        while (!cursor!!.isAfterLast) {
            listOfTVs.add(TVSeriesResult(cursor))
            cursor.moveToNext()
        }
        cursor.close()

        return Observable.fromArray(listOfTVs)
    }
}