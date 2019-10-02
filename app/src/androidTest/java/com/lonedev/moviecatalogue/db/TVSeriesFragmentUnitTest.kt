/*
 * Created by Bagus Yogatama on 9/22/19 11:05 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 10:26 PM
 */

package com.lonedev.moviecatalogue.db

import androidx.paging.DataSource
import androidx.room.paging.LimitOffsetDataSource
import com.lonedev.moviecatalogue.base.shceduler.TestSchedulerProvider
import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import com.lonedev.moviecatalogue.data.models.TVSeriesResult
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import com.lonedev.moviecatalogue.db.di.DaggerInstrumentTestComponent
import com.lonedev.moviecatalogue.ui.main.main.fragment.tv.TVSeriesViewModel
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@RunWith(JUnit4::class)
class TVSeriesFragmentUnitTest {
    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var tvSeriesDao: TVSeriesDao

    private lateinit var tvSeriesReporsitory: TVSeriesRepository
    private lateinit var tvSeriesViewModel: TVSeriesViewModel

    private lateinit var factory: DataSource.Factory<Int, TVSeriesResult>

    @Before
    fun setUp() {
        DaggerInstrumentTestComponent.builder()
            .build().tvSeriesFragmentTest(this)

        val testScheduler = TestScheduler()

        tvSeriesReporsitory = TVSeriesRepository(movieApi, tvSeriesDao)
        tvSeriesViewModel = TVSeriesViewModel(
            tvSeriesReporsitory,
            TestSchedulerProvider(testScheduler)
        )

        factory = tvSeriesDao.getTVSeries()
    }

    @Test
    fun getPaginatedTVSeries() {
        (factory.create() as LimitOffsetDataSource).loadRange(0, 10)
    }

    @Test
    fun getTVSeriesFromNetwork() {
        tvSeriesReporsitory.getTVSeriesFromNetwork(1).test()
            .assertValue {
                it.isNotEmpty()
            }

        tvSeriesReporsitory.getTVSeriesFromNetwork(1).test()
            .assertValue {
                it.size > 10
            }
    }
}