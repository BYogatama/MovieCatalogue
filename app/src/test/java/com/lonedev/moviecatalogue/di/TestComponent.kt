/*
 * Created by Bagus Yogatama on 8/22/19 11:25 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/22/19 11:25 PM
 */

package com.lonedev.moviecatalogue.di

import com.lonedev.moviecatalogue.MovieListTest
import com.lonedev.moviecatalogue.TVListTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModuleTest::class]
)
interface TestComponent {
    fun movieFragmentTest(movieListTest: MovieListTest)
    fun tvSeriesFragmentTest(tvListTest: TVListTest)
}