/*
 * Created by Bagus Yogatama on 8/31/19 3:27 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/31/19 3:27 PM
 */

package com.lonedev.moviecatalogue

import com.lonedev.moviecatalogue.data.local.dao.TVSeriesDao
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.TVSeriesRepository
import com.lonedev.moviecatalogue.di.DaggerTestComponent
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class TVListTest {
    @Inject
    lateinit var movieApi: MovieApi
    @Inject
    lateinit var tvSeriesDao: TVSeriesDao

    private lateinit var tvSeriesReporsitory: TVSeriesRepository

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .build().tvSeriesFragmentTest(this)

        tvSeriesReporsitory = TVSeriesRepository(movieApi, tvSeriesDao)
    }

    @Test
    fun `getTVSeries from network and make sure has value`() {
        tvSeriesReporsitory.getTvSeries().test()
            .assertValue{
                it.isNotEmpty()
            }
    }

    @Test
    fun `getTVSeries from network and make sure initial size more than 10`() {
        tvSeriesReporsitory.getTvSeries().test()
            .assertValue {
                it.size > 10
            }
    }
}