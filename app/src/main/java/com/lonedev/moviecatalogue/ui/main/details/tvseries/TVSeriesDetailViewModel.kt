/*
 * Created by Bagus Yogatama on 6/29/19 10:48 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/29/19 10:48 AM
 */

package com.lonedev.moviecatalogue.ui.main.details.tvseries

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.FavoriteRepository
import com.lonedev.moviecatalogue.data.TVSeriesRepository
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.data.models.Video
import com.lonedev.moviecatalogue.data.models.VideoResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVSeriesDetailViewModel
@Inject constructor(
    private val tvSeriesRepository: TVSeriesRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    var getVideoResult: MutableLiveData<Video<VideoResult>> = MutableLiveData()
    var getVideoError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<Video<VideoResult>>

    var favouritesResult: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableSaveFavourites: DisposableCompletableObserver

    var getFavouritedResult: MutableLiveData<TVSeriesResult> = MutableLiveData()
    var getFavouritedError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableGetFavourites: DisposableSingleObserver<TVSeriesResult>

    lateinit var disposableDeleteFavourites: DisposableCompletableObserver

    fun onGetVideoResult(): LiveData<Video<VideoResult>> {
        return getVideoResult
    }

    fun onGetVideoError(): LiveData<String> {
        return getVideoError
    }

    fun onGetFavouritedResult(): LiveData<TVSeriesResult> {
        return getFavouritedResult
    }

    fun onGetFavouritedError(): LiveData<String> {
        return getFavouritedError
    }

    fun onSuccessSaveFavorites(): LiveData<String> {
        return favouritesResult
    }

    fun onSuccessDeleteFavorites(): LiveData<String> {
        return favouritesResult
    }

    fun saveFavourites(context: Context, tvSeriesId: Int) {

        disposableSaveFavourites = object : DisposableCompletableObserver() {
            override fun onComplete() {
                favouritesResult.postValue(context.getString(R.string.success_favourties))
            }

            override fun onError(e: Throwable) {
                favouritesResult.postValue(context.getString(R.string.failed_favourties))
            }

        }

        favoriteRepository.saveFavourites(tvSeriesId)
            .subscribeOn(Schedulers.io())
            .subscribe(disposableSaveFavourites)
    }

    fun deleteFavourites(context: Context, tvSeriesId: Int) {

        disposableDeleteFavourites = object : DisposableCompletableObserver() {
            override fun onComplete() {
                favouritesResult.postValue(context.getString(R.string.success_remove_favourites))
            }

            override fun onError(e: Throwable) {
                favouritesResult.postValue(context.getString(R.string.failed_remove_favourites))
            }

        }

        favoriteRepository.deleteFavourites(tvSeriesId)
            .subscribeOn(Schedulers.io())
            .subscribe(disposableDeleteFavourites)
    }

    fun getFavoritedTVSeries(tvSeriesId: Int) {

        disposableGetFavourites = object : DisposableSingleObserver<TVSeriesResult>() {
            override fun onSuccess(tvSeries: TVSeriesResult) {
                getFavouritedResult.postValue(tvSeries)
            }

            override fun onError(e: Throwable) {
                getFavouritedError.postValue(e.message)
            }

        }

        favoriteRepository.getFavoritedTVSeries(tvSeriesId)
            .subscribeOn(Schedulers.io())
            .subscribe(disposableGetFavourites)
    }

    fun getVideos(tvId: Int) {

        disposableObserver = object : DisposableObserver<Video<VideoResult>>() {
            override fun onComplete() {
            }

            override fun onNext(video: Video<VideoResult>) {
                getVideoResult.postValue(video)
            }

            override fun onError(e: Throwable) {
                getVideoError.postValue(e.message)
            }
        }

        tvSeriesRepository.getTvVideos(tvId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
        if (!disposableGetFavourites.isDisposed) disposableGetFavourites.dispose()

    }

    fun disposeSaveFavourites() {
        if (!disposableSaveFavourites.isDisposed) disposableSaveFavourites.dispose()
    }

    fun disposeDeleteFavourites() {
        if (!disposableDeleteFavourites.isDisposed) disposableDeleteFavourites.dispose()
    }

}