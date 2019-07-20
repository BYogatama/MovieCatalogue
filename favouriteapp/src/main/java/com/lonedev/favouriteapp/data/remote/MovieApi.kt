/*
 * Created by Bagus Yogatama on 7/20/19 8:04 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:36 PM
 */

package com.lonedev.favouriteapp.data.remote

import com.lonedev.favouriteapp.data.models.Video
import com.lonedev.favouriteapp.data.models.VideoResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

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
}