/*
 * Created by Bagus Yogatama on 6/26/19 6:34 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/26/19 6:34 PM
 */

package com.lonedev.moviecatalogue.data.remote

import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Movie<MovieResult>>

    @GET("discover/tv")
    fun getTvSeries(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Movie<TVSeriesResult>>

}