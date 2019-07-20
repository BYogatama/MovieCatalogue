/*
 * Created by Bagus Yogatama on 7/2/19 3:50 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 3:50 PM
 */

package com.lonedev.moviecatalogue.ui.main.favorite.fragment.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.repositories.FavoriteRepository
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouritesTVViewModel @Inject
constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    var getFavoritesResult: MutableLiveData<List<TVSeriesResult>> = MutableLiveData()
    var getFavoritesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableFavorite: DisposableObserver<List<TVSeriesResult>>

    fun onGetFavoritesResult(): LiveData<List<TVSeriesResult>> {
        return getFavoritesResult
    }

    fun onGetFavoritesError(): LiveData<String> {
        return getFavoritesError
    }

    fun getFavorites() {

        disposableFavorite = object : DisposableObserver<List<TVSeriesResult>>() {
            override fun onComplete() {
            }

            override fun onNext(movies: List<TVSeriesResult>) {
                getFavoritesResult.postValue(movies)
            }

            override fun onError(e: Throwable) {
                getFavoritesError.postValue(e.message)
            }
        }

        favoriteRepository.getFavoriteTVSeries()
            .subscribeOn(Schedulers.io())
            .subscribe(disposableFavorite)

    }

    fun disposeElements() {
        if (!disposableFavorite.isDisposed) disposableFavorite.dispose()
    }
}