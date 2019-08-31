/*
 * Created by Bagus Yogatama on 8/31/19 5:08 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 5:07 PM
 */

package com.lonedev.moviecatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lonedev.moviecatalogue.base.shceduler.TestSchedulerProvider
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.SearchRepository
import com.lonedev.moviecatalogue.di.DaggerTestComponent
import com.lonedev.moviecatalogue.ui.main.main.fragment.search.SearchViewModel
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
class SearchFragmentTest {
    @Inject
    lateinit var movieApi: MovieApi

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var searchRepository: SearchRepository
    private lateinit var searcViewModel: SearchViewModel

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .build().searchFragmentTest(this)

        val testScheduler = TestScheduler()

        searchRepository = SearchRepository(movieApi)
        searcViewModel = SearchViewModel(searchRepository, TestSchedulerProvider(testScheduler))
        searcViewModel.search("batman")
    }

    @Test
    fun searchMoviesTVs() {
        searcViewModel.onSuccessSearch().observeForever {
            !it.isNullOrEmpty()
        }
    }
}