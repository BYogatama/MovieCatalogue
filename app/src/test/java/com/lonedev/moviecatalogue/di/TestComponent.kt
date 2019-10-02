/*
 * Created by Bagus Yogatama on 9/22/19 11:16 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 11:14 PM
 */

package com.lonedev.moviecatalogue.di

import com.lonedev.moviecatalogue.SearchFragmentTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModuleTest::class]
)
interface TestComponent {
    fun searchFragmentTest(searchFragmentTest: SearchFragmentTest)
}