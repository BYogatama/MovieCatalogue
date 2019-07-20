/*
 * Created by Bagus Yogatama on 6/28/19 4:45 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 4:45 PM
 */

package com.lonedev.moviecatalogue.data.repositories

import com.lonedev.moviecatalogue.BuildConfig
import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
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
class TVSeriesRepository @Inject constructor(
    private val movieApi: MovieApi,
    private val tvSeriesDao: TVSeriesDao
) {


    fun getTvSeries(): Observable<List<TVSeriesResult>> {
        val observableFromNetwork = getTVSeriesFromNetwork()
        val observableFromLocal = getTVSeriesFromLocal()
        return Observable.concatArrayEager(observableFromNetwork, observableFromLocal)
    }

    /**
     * Get TV Series from Network and return it as Observable<Movie<TVSeriesResult>>
     *     for usage in TVSeriesViewModel. MemberApi is Injected using DI in this class
     *     so it can be use directly in this class
     */
    private fun getTVSeriesFromNetwork(): Observable<List<TVSeriesResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        if (language == "in-ID") {
            language = "id-ID"
        }

        return movieApi.getTvSeries(BuildConfig.API_KEY, language)
            .map {
                return@map it.results
            }
            .doOnNext {
                for (item in it) {
                    tvSeriesDao.saveTVSeries(item)
                }
            }
    }

    /**
     * Get Movies from LocalDB and return it as Observable<List<MovieResult>>
     *     for usage in MovieViewModel. MovieDao is Injected using DI in this class
     *     so it can be use directly in this class
     */
    private fun getTVSeriesFromLocal(): Observable<List<TVSeriesResult>> {
        return tvSeriesDao.getTVSeries().toObservable()
    }

    fun getTvVideos(movieId: Int): Observable<Video<VideoResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        // No videos using Bahasa Indonesia so default is en-US for Videos
        if (language == "in-ID") {
            language = "en-US"
        }

        return movieApi.getTvVideos(movieId, BuildConfig.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}