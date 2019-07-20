/*
 * Created by Bagus Yogatama on 7/20/19 7:25 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:25 PM
 */

package com.lonedev.moviecatalogue.ui.main.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import com.lonedev.moviecatalogue.utils.Preferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val tvSeriesRepository: TVSeriesRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    fun setupMovieReleaseReminder(context: Context?) {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        compositeDisposable.add(
            movieRepository.getMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    for (movie in it) {
                        if (movie.releaseDate == currentDate) {
                            Preferences.setupMovieReleaseReminder(context, movie)
                        }
                    }
                }, Throwable::printStackTrace)
        )
    }

    fun setupTVReleaseReminder(context: Context?) {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        compositeDisposable.add(
            tvSeriesRepository.getTvSeries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    for (tv in it) {
                        if (tv.firstAirDate == currentDate) {
                            Preferences.setupTVSeriesReleaseReminder(context, tv)
                        }
                    }
                }, Throwable::printStackTrace)
        )
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }

}