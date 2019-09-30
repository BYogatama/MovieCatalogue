/*
 * Created by Bagus Yogatama on 7/2/19 4:08 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 2:23 PM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.lonedev.moviecatalogue.base.shceduler.BaseSchedulerProvider
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import com.lonedev.moviecatalogue.utils.IdlingResources
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TVSeriesViewModel @Inject constructor(
    private val repository: TVSeriesRepository,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    fun onGetTVSeries(): LiveData<PagedList<TVSeriesResult>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .setPageSize(10)
            .build()

        return LivePagedListBuilder(repository.getDataSourceFactory(), config).build()
    }

    fun getTVSeries(page: Int): Observable<List<TVSeriesResult>> {
        IdlingResources.increment()
        return repository.getTVSeriesFromNetwork(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .debounce(400, TimeUnit.MILLISECONDS)
            .doOnNext {
                if(!IdlingResources.getIdlingResource().isIdleNow) {
                    IdlingResources.decrement()
                }
            }
    }


}