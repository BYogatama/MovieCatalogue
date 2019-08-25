/*
 * Created by Bagus Yogatama on 8/23/19 12:06 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 8/23/19 12:06 AM
 */

package com.lonedev.moviecatalogue.di

import android.content.Context
import com.lonedev.moviecatalogue.di.modules.NetworkModule
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModuleTest::class])
class AppModuleTest {
    @Singleton
    @Provides
    fun provideAppContext(): Context = mockk()
}