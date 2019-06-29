/*
 * Created by Bagus Yogatama on 6/28/19 4:45 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 4:45 PM
 */

package com.lonedev.moviecatalogue.data

import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.utils.Constant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


class TVSeriesRepository @Inject constructor(private val movieApi: MovieApi) {

    /**
     * Get TV Series from Network and return it as Observable<Movie<TVSeriesResult>>
     *     for usage in TVSeriesViewModel. MemberApi is Injected using DI in this class
     *     so it can be use directly in this class
     */
    fun getTVSeriesFromNetwork(): Observable<Movie<TVSeriesResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        // Warning
        // Not Every movie has indoneisan translation
        if(language == "in-ID"){
            language = "id-ID"
        }

        return movieApi.getTvSeries(Constant.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTvVideos(movieId: Int): Observable<Video<VideoResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_","-")

        // No videos using Bahasa Indonesia so default is en-US for Videos
        if(language == "in-ID"){
            language = "en-US"
        }

        return movieApi.getTvVideos(movieId, Constant.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}