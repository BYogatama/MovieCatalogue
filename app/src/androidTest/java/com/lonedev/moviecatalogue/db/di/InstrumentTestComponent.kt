/*
 * Created by Bagus Yogatama on 9/22/19 11:06 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 10:55 PM
 */

package com.lonedev.moviecatalogue.db.di

import com.lonedev.moviecatalogue.db.MovieFragmentUnitTest
import com.lonedev.moviecatalogue.db.TVSeriesFragmentUnitTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModuleTest::class, DatabaseModuleTest::class]
)
interface InstrumentTestComponent {
    fun movieFragmentTest(movieFragmentTest: MovieFragmentUnitTest)
    fun tvSeriesFragmentTest(tvSeriesFragmentTest: TVSeriesFragmentUnitTest)
}