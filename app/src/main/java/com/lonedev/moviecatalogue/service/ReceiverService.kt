/*
 * Created by Bagus Yogatama on 7/22/19 1:16 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/22/19 1:16 PM
 */

package com.lonedev.moviecatalogue.service

import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReceiverService @Inject constructor(
    private val tvSeriesRepository: TVSeriesRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val currentDate: String = dateFormat.format(Date())


    fun getCurrentReleasedMovie(): Observable<MovieResult> {
        return movieRepository.getMovies()
            .flatMap { Observable.fromIterable(it) }
            .filter {
                it.releaseDate == currentDate
            }
            .subscribeOn(Schedulers.io())


    }

    fun getCurrentReleasedTV(): Observable<TVSeriesResult> {
        return tvSeriesRepository.getTvSeries()
            .flatMap { Observable.fromIterable(it) }
            .filter {
                it.firstAirDate == currentDate
            }
            .subscribeOn(Schedulers.io())
    }
}