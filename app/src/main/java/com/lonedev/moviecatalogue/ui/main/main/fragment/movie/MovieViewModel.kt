/*
 * Created by Bagus Yogatama on 7/2/19 2:31 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:23 PM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lonedev.moviecatalogue.base.shceduler.BaseSchedulerProvider
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    fun getMovies(page: Int): Observable<List<MovieResult>> {
        return repository.getMoviesFromNetwork(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .debounce(400, TimeUnit.MILLISECONDS)
    }

    fun onGetMovies(): LiveData<PagedList<MovieResult>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(repository.getDataSourceFactory(), config).build()
    }


}