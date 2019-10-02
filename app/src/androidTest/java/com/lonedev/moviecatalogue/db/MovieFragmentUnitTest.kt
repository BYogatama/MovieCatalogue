/*
 * Created by Bagus Yogatama on 9/22/19 10:50 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 10:50 PM
 */

package com.lonedev.moviecatalogue.db

import androidx.paging.DataSource
import androidx.room.paging.LimitOffsetDataSource
import androidx.test.runner.AndroidJUnit4
import com.lonedev.moviecatalogue.base.shceduler.TrampolineSchedulerProvider
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.models.MovieResult
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import com.lonedev.moviecatalogue.db.di.DaggerInstrumentTestComponent
import com.lonedev.moviecatalogue.ui.main.main.fragment.movie.MovieViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MovieFragmentUnitTest {

    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var moviesDao: MoviesDao

    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel

    private lateinit var factory : DataSource.Factory<Int, MovieResult>

    @Before
    fun setUp() {
        DaggerInstrumentTestComponent.builder()
            .build().movieFragmentTest(this)


        movieRepository = MovieRepository(movieApi, moviesDao)
        movieViewModel = MovieViewModel(
            movieRepository,
            TrampolineSchedulerProvider()
        )

        factory = moviesDao.getMovies()
    }

    @Test
    fun getPaginatedMovie(){
        (factory.create() as LimitOffsetDataSource).loadRange(0, 10)
    }

    @Test
    fun getMoviesFromNetwork() {
        movieRepository.getMoviesFromNetwork(1).test()
            .assertValue {
                it.isNotEmpty()
            }

        movieRepository.getMoviesFromNetwork(1).test()
            .assertValue {
                it.size > 10
            }
    }


}