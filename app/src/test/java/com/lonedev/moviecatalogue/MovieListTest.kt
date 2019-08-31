/*
 * Created by Bagus Yogatama on 8/3/19 3:26 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/3/19 3:26 PM
 */

package com.lonedev.moviecatalogue

import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import com.lonedev.moviecatalogue.di.DaggerTestComponent
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
class MovieListTest {

    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var moviesDao: MoviesDao

    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .build().movieFragmentTest(this)

        movieRepository = MovieRepository(movieApi, moviesDao)
    }

    @Test
    fun `getMovies from network and make sure has value`() {
        movieRepository.getMoviesFromNetwork().test()
            .assertValue {
                it.isNotEmpty()
            }
    }

    @Test
    fun `getMovies from network and make sure initial size more than 10`() {
        movieRepository.getMoviesFromNetwork().test()
            .assertValue {
                it.size > 10
            }
    }


}