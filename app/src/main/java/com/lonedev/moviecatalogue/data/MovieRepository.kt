/*
 * Created by Bagus Yogatama on 6/28/19 3:44 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 3:44 PM
 */

package com.lonedev.moviecatalogue.data

import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.utils.Constant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    /**
     * Get Movies from Network and return it as Observable<Movie<MovieResult>>
     *     for usage in MovieViewModel. MemberApi is Injected using DI in this class
     *     so it can be use directly in this class
     */
    fun getMoviesFromNetwork(): Observable<Movie<MovieResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_","-")

        // Not Every movie has indoneisan translation
        if(language == "in-ID"){
            language = "id-ID"
        }

        return movieApi.getMovies(Constant.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getMovieVideos(movieId: Int): Observable<Video<VideoResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_","-")

        // No videos using Bahasa Indonesia so default is en-US for Videos
        if(language == "in-ID"){
            language = "en-US"
        }

        return movieApi.getMovieVideos(movieId, Constant.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}