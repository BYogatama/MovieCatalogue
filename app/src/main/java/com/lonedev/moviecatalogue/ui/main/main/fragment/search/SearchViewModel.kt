/*
 * Created by Bagus Yogatama on 7/18/19 1:22 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 1:22 AM
 */

package com.lonedev.moviecatalogue.ui.main.main.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.base.shceduler.BaseSchedulerProvider
import com.lonedev.moviecatalogue.data.models.SearchResult
import com.lonedev.moviecatalogue.data.repositories.SearchRepository
import com.lonedev.moviecatalogue.utils.IdlingResources
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {

    var searchResult: MutableLiveData<List<SearchResult>> = MutableLiveData()
    var searchError: MutableLiveData<String> = MutableLiveData()

    lateinit var disposableObserver: DisposableObserver<List<SearchResult>>


    fun onSuccessSearch(): LiveData<List<SearchResult>> {
        return searchResult
    }

    fun onErrorSearch(): LiveData<String> {
        return searchError
    }

    fun search(query: String?) {
        IdlingResources.increment()
        disposableObserver = object : DisposableObserver<List<SearchResult>>() {
            override fun onComplete() {
                if (!IdlingResources.getIdlingResource().isIdleNow) {
                    IdlingResources.decrement()
                }
            }

            override fun onNext(searchResults: List<SearchResult>) {
                this@SearchViewModel.searchResult.postValue(searchResults)
            }

            override fun onError(e: Throwable) {
                searchError.postValue(e.localizedMessage)
            }

        }

        searchRepository.search(query)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)

    }

    fun disposeElements() {
        if (!disposableObserver.isDisposed) disposableObserver.dispose()
    }


}