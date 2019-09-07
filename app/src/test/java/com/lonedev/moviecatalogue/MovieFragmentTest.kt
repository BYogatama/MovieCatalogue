/*
 * Created by Bagus Yogatama on 8/31/19 5:07 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 5:04 PM
 */

package com.lonedev.moviecatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lonedev.moviecatalogue.base.shceduler.TestSchedulerProvider
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import com.lonedev.moviecatalogue.di.DaggerTestComponent
import com.lonedev.moviecatalogue.ui.main.main.fragment.movie.MovieViewModel
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
class MovieFragmentTest {

    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var moviesDao: MoviesDao

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .build().movieFragmentTest(this)

        val testScheduler = TestScheduler()
        movieRepository = MovieRepository(movieApi, moviesDao)
        movieViewModel = MovieViewModel(
            movieRepository,
            TestSchedulerProvider(testScheduler)
        )
    }

    @Test
    fun getMoviesFromNetwork(){
        movieRepository.getMoviesFromNetwork().test()
            .assertValue{
                it.isNotEmpty()
            }

        movieRepository.getMoviesFromNetwork().test()
            .assertValue{
                it.size > 10
            }
    }

    @Test
    fun getMovies() {
        movieViewModel.getMovies()
        movieViewModel.onSuccessGetMovies().observeForever {
            it.isNotEmpty()
        }

        movieViewModel.onSuccessGetMovies().observeForever {
            it.size > 10
        }
    }


}