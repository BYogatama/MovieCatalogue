/*
 * Created by Bagus Yogatama on 9/22/19 11:14 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 11:06 PM
 */

package com.lonedev.moviecatalogue.di

import android.content.Context
import com.lonedev.moviecatalogue.di.modules.NetworkModule
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class AppModuleTest {
    @Singleton
    @Provides
    fun provideAppContext(): Context = mockk()
}