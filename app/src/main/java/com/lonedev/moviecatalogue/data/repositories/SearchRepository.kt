/*
 * Created by Bagus Yogatama on 7/19/19 5:59 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/19/19 5:58 AM
 */

package com.lonedev.moviecatalogue.data.repositories

import com.lonedev.moviecatalogue.BuildConfig
import com.lonedev.moviecatalogue.data.models.SearchResult
import com.lonedev.moviecatalogue.data.remote.MovieApi
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class SearchRepository @Inject constructor(
    private val movieApi: MovieApi
) {

    fun search(query: String?): Observable<List<SearchResult>> {
        val observableFromMovie = searchMovies(query)
        val observableFromTV = searchTV(query)
        return Observable.zip(observableFromMovie, observableFromTV,
            BiFunction { movies, tvs ->
                val finalResults: MutableList<SearchResult> = ArrayList()
                finalResults.addAll(movies)
                finalResults.addAll(tvs)
                finalResults.sortBy { it.name }
                finalResults
            })
    }

    private fun searchMovies(query: String?): Observable<List<SearchResult>> {

        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        if (language == "in-ID") {
            language = "id-ID"
        }

        return movieApi.searchMovies(BuildConfig.API_KEY, language, query)
            .map {
                return@map it.results
            }

    }

    private fun searchTV(query: String?): Observable<List<SearchResult>> {
        var language = Locale.getDefault().toString()
        language = language.replace("_", "-")

        if (language == "in-ID") {
            language = "id-ID"
        }

        return movieApi.searchTV(BuildConfig.API_KEY, language, query)
            .map {
                return@map it.results
            }
    }

}