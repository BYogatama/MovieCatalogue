/*
 * Created by Bagus Yogatama on 6/29/19 8:13 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 8:13 AM
 */

package com.lonedev.moviecatalogue.ui.main.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.MovieRepository
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {


    var videoResult: MutableLiveData<Video<VideoResult>> = MutableLiveData()
    var videoError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<Video<VideoResult>>

    fun videoResult(): LiveData<Video<VideoResult>> {
        return videoResult
    }

    fun videoError(): LiveData<String> {
        return videoError
    }

    fun loadVideos(movieId: Int) {

        disposableObserver = object : DisposableObserver<Video<VideoResult>>() {
            override fun onComplete() {
            }

            override fun onNext(video: Video<VideoResult>) {
                videoResult.postValue(video)
            }

            override fun onError(e: Throwable) {
                videoError.postValue(e.message)
            }
        }

        movieRepository.getMovieVideos(movieId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }

}