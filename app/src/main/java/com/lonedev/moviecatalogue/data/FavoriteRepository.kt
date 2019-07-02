/*
 * Created by Bagus Yogatama on 7/2/19 2:01 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:01 PM
 */

package com.lonedev.moviecatalogue.data

import com.lonedev.moviecatalogue.data.local.dao.FavouritesDao
import com.lonedev.moviecatalogue.data.models.Favourites
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favouritesDao: FavouritesDao
) {

    fun saveFavourites(movieId: Int): Completable {
        val favourites = Favourites(0, movieId, Favourites.Type.MOVIES)
        return favouritesDao.saveFavourites(favourites)
    }

    fun deleteFavourites(favoriteId: Int): Completable {
        return favouritesDao.deleteFavourites(favoriteId)
    }

    fun getFavoritedMovie(movieId: Int): Single<MovieResult> {
        return favouritesDao.getFavoritedMovies(movieId)
    }

    fun getFavoritedTVSeries(movieId: Int): Single<TVSeriesResult> {
        return favouritesDao.getFavoritedTVSeries(movieId)
    }

    fun getFavoriteMovies(): Observable<List<MovieResult>> {
        return favouritesDao.getFavouriteMovies()
    }

    fun getFavoriteTVSeries(): Observable<List<TVSeriesResult>> {
        return favouritesDao.getFavouriteTVSeries()
    }
}