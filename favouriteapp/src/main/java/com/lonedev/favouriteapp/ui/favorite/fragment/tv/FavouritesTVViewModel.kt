/*
 * Created by Bagus Yogatama on 7/20/19 7:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:36 PM
 */

package com.lonedev.favouriteapp.ui.favorite.fragment.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.favouriteapp.data.models.MovieResult
import com.lonedev.favouriteapp.data.models.TVSeriesResult
import com.lonedev.favouriteapp.data.repositories.FavoriteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouritesTVViewModel @Inject
constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

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

            override fun onNext(tvs: List<TVSeriesResult>) {
                getFavoritesResult.postValue(tvs)
            }

            override fun onError(e: Throwable) {
                getFavoritesError.postValue(e.message)
            }
        }

        favoriteRepository.getFavoriteTVSeries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableFavorite)

    }

    fun disposeElements() {
        if (!disposableFavorite.isDisposed) disposableFavorite.dispose()
    }
}