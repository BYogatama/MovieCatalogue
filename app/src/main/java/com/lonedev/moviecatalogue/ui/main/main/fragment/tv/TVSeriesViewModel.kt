/*
 * Created by Bagus Yogatama on 7/2/19 4:08 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:23 PM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVSeriesViewModel @Inject constructor(private val tvSeriesRepository: TVSeriesRepository) : ViewModel() {

    var tvSeriesResult: MutableLiveData<List<TVSeriesResult>> = MutableLiveData()
    var tvSeriesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<TVSeriesResult>>

    fun tvSeriesResult(): LiveData<List<TVSeriesResult>> {
        return tvSeriesResult
    }

    fun tvSeriesError(): LiveData<String> {
        return tvSeriesError
    }

    fun loadTVSeries() {

        disposableObserver = object : DisposableObserver<List<TVSeriesResult>>() {
            override fun onComplete() {
            }

            override fun onNext(tvSeries: List<TVSeriesResult>) {
                tvSeriesResult.postValue(tvSeries)
            }

            override fun onError(e: Throwable) {
                tvSeriesError.postValue(e.message)
            }
        }

        tvSeriesRepository.getTvSeries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }

}