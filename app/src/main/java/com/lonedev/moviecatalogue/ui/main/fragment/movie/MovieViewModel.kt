/*
 * Created by Bagus Yogatama on 6/28/19 10:42 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 10:39 PM
 */

package com.lonedev.moviecatalogue.ui.main.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.MovieRepository
import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.MovieResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    var movieResult: MutableLiveData<Movie<MovieResult>> = MutableLiveData()
    var movieError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<Movie<MovieResult>>

    fun movieResult(): LiveData<Movie<MovieResult>> {
        return movieResult
    }

    fun movieError(): LiveData<String> {
        return movieError
    }

    fun loadMovies() {

        disposableObserver = object : DisposableObserver<Movie<MovieResult>>() {
            override fun onComplete() {
            }

            override fun onNext(movie: Movie<MovieResult>) {
                movieResult.postValue(movie)
            }

            override fun onError(e: Throwable) {
                movieError.postValue(e.message)
            }
        }

        movieRepository.getMoviesFromNetwork()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }


}