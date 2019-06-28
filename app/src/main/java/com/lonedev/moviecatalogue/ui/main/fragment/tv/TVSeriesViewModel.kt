/*
 * Created by Bagus Yogatama on 6/28/19 10:44 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 10:41 PM
 */

package com.lonedev.moviecatalogue.ui.main.fragment.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.TVSeriesRepository
import com.lonedev.moviecatalogue.data.models.Movie
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TVSeriesViewModel @Inject constructor(private val tvSeriesRepository: TVSeriesRepository) : ViewModel() {

    var tvSeriesResult: MutableLiveData<Movie<TVSeriesResult>> = MutableLiveData()
    var tvSeriesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<Movie<TVSeriesResult>>

    fun tvSeriesResult(): LiveData<Movie<TVSeriesResult>> {
        return tvSeriesResult
    }

    fun tvSeriesError(): LiveData<String> {
        return tvSeriesError
    }

    fun loadTVSeries() {

        disposableObserver = object : DisposableObserver<Movie<TVSeriesResult>>() {
            override fun onComplete() {
            }

            override fun onNext(tvSeries: Movie<TVSeriesResult>) {
                tvSeriesResult.postValue(tvSeries)
            }

            override fun onError(e: Throwable) {
                tvSeriesError.postValue(e.message)
            }
        }

        tvSeriesRepository.getTVSeriesFromNetwork()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }

}