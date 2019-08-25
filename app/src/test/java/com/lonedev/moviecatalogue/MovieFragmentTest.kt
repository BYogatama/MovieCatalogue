/*
 * Created by Bagus Yogatama on 8/3/19 3:26 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/3/19 3:26 PM
 */

package com.lonedev.moviecatalogue

import androidx.lifecycle.LiveData
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import com.lonedev.moviecatalogue.di.DaggerTestComponent
import com.lonedev.moviecatalogue.ui.main.main.fragment.movie.MovieViewModel
import com.lonedev.moviecatalogue.utils.OneTimeObserver
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class MovieFragmentTest {

    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var moviesDao: MoviesDao

    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .build().movieFragmentTest(this)

        movieRepository = MovieRepository(movieApi, moviesDao)
        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun testGetMovies() {
    }

    private fun <T> LiveData<T>.observeOnce(onChangeHandler: (T) -> Unit) {
        val observer = OneTimeObserver(handler = onChangeHandler)
        observe(observer, observer)
    }

}