/*
 * Created by Bagus Yogatama on 6/26/19 6:34 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/26/19 6:34 PM
 */

package com.lonedev.moviecatalogue.data.remote

import com.lonedev.moviecatalogue.data.models.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Video<VideoResult>>

    @GET("tv/{tv_id}/videos")
    fun getTvVideos(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<Video<VideoResult>>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String?
    ): Observable<Movie<SearchResult>>

    @GET("search/tv")
    fun searchTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String?
    ): Observable<Movie<SearchResult>>
}