/*
 * Created by Bagus Yogatama on 7/2/19 2:24 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:24 PM
 */

package com.lonedev.moviecatalogue.ui.main.favorite.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.data.repositories.FavoriteRepository
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.utils.IdlingResources
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