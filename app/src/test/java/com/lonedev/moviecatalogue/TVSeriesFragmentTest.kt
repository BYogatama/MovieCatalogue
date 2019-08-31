/*
 * Created by Bagus Yogatama on 8/31/19 5:07 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 5:05 PM
 */

package com.lonedev.moviecatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lonedev.moviecatalogue.base.shceduler.TestSchedulerProvider
import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import com.lonedev.moviecatalogue.di.DaggerTestComponent
import com.lonedev.moviecatalogue.ui.main.main.fragment.tv.TVSeriesViewModel
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
class TVSeriesFragmentTest {
    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var tvSeriesDao: TVSeriesDao

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var tvSeriesReporsitory: TVSeriesRepository
    private lateinit var tvSeriesViewModel: TVSeriesViewModel

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .build().tvSeriesFragmentTest(this)

        val testScheduler = TestScheduler()

        tvSeriesReporsitory = TVSeriesRepository(movieApi, tvSeriesDao)
        tvSeriesViewModel = TVSeriesViewModel(
            tvSeriesReporsitory,
            TestSchedulerProvider(testScheduler)
        )
        tvSeriesViewModel.getTVSeries()
    }

    @Test
    fun getTVSeries() {
        tvSeriesViewModel.onSuccessGetTVSeries().observeForever {
            it.isNotEmpty()
        }

        tvSeriesViewModel.onSuccessGetTVSeries().observeForever {
            it.size > 10
        }
    }
}