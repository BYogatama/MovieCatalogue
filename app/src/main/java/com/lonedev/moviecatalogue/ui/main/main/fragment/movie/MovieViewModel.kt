/*
 * Created by Bagus Yogatama on 7/2/19 2:31 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:23 PM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.MovieRepository
import com.lonedev.moviecatalogue.data.models.MovieResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

    var movieResult: MutableLiveData<List<MovieResult>> = MutableLiveData()
    var movieError: MutableLiveData<String> = MutableLiveData()

    lateinit var disposableObserver: DisposableObserver<List<MovieResult>>

    fun onSuccessGetMovies(): LiveData<List<MovieResult>> {
        return movieResult
    }

    fun onErrorGetMovies(): LiveData<String> {
        return movieError
    }

    fun getMovies() {

        disposableObserver = object : DisposableObserver<List<MovieResult>>() {
            override fun onComplete() {
            }

            override fun onNext(movie: List<MovieResult>) {
                movieResult.postValue(movie)
            }

            override fun onError(e: Throwable) {
                movieError.postValue(e.message)
                e.printStackTrace()
            }
        }

        movieRepository.getMovies()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }


}