/*
 * Created by Bagus Yogatama on 6/28/19 3:44 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 3:44 PM
 */

package com.lonedev.moviecatalogue.data.repositories

import androidx.paging.DataSource
import com.lonedev.moviecatalogue.BuildConfig
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.data.remote.MovieApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val moviesDao: MoviesDao
) {

    fun getMoviesFromNetwork(page : Int): Observable<List<MovieResult>> {
        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        if (language == "in-ID") {
            language = "id-ID"
        }

        return movieApi.getMovies(BuildConfig.API_KEY, language, page)
            .map {
                return@map it.results
            }
            .doOnNext {
                for (item in it) {
                    moviesDao.saveMovie(item)
                }
            }
    }


    fun getDataSourceFactory(): DataSource.Factory<Int, MovieResult> {
        return moviesDao.getMovies()
    }

    fun getMovieVideos(movieId: Int): Observable<Video<VideoResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        if (language == "in-ID") {
            language = "en-US"
        }

        return movieApi.getMovieVideos(movieId, BuildConfig.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}