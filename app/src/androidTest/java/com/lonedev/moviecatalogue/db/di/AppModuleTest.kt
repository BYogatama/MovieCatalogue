/*
 * Created by Bagus Yogatama on 9/22/19 11:06 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 9/22/19 10:57 PM
 */

package com.lonedev.moviecatalogue.db.di

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.lonedev.moviecatalogue.di.modules.NetworkModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModuleTest::class])
class AppModuleTest {
    @Singleton
    @Provides
    fun provideAppContext(): Context = ApplicationProvider.getApplicationContext()
}