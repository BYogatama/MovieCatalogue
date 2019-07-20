/*
 * Created by Bagus Yogatama on 7/20/19 7:57 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:36 PM
 */

package com.lonedev.favouriteapp.ui.favorite.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.favouriteapp.data.models.MovieResult
import com.lonedev.favouriteapp.data.repositories.FavoriteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouritesMoviesViewModel @Inject
constructor(private val favoriteRepository: FavoriteRepository) : ViewModel() {

    var getFavoritesResult: MutableLiveData<List<MovieResult>> = MutableLiveData()
    var getFavoritesError: MutableLiveData<String> = MutableLiveData()
    lateinit var disposableFavorite: DisposableObserver<List<MovieResult>>

    fun onGetFavoritesResult(): LiveData<List<MovieResult>> {
        return getFavoritesResult
    }

    fun onGetFavoritesError(): LiveData<String> {
        return getFavoritesError
    }

    fun getFavorites() {

        disposableFavorite = object : DisposableObserver<List<MovieResult>>() {
            override fun onComplete() {
            }

            override fun onNext(movies: List<MovieResult>) {
                getFavoritesResult.postValue(movies)
            }

            override fun onError(e: Throwable) {
                getFavoritesError.postValue(e.message)
            }
        }

        favoriteRepository.getFavoriteMovies()
            .subscribeOn(Schedulers.io())
            .subscribe(disposableFavorite)

    }

    fun disposeElements() {
        if (!disposableFavorite.isDisposed) disposableFavorite.dispose()
    }


}