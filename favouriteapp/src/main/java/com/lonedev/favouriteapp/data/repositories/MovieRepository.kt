/*
 * Created by Bagus Yogatama on 7/20/19 8:04 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:36 PM
 */

package com.lonedev.favouriteapp.data.repositories

import com.lonedev.favouriteapp.BuildConfig
import com.lonedev.favouriteapp.data.models.Video
import com.lonedev.favouriteapp.data.models.VideoResult
import com.lonedev.favouriteapp.data.remote.MovieApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) {

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