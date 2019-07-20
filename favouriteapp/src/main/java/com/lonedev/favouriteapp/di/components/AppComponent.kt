/*
 * Created by Bagus Yogatama on 7/20/19 8:22 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 3:43 PM
 */

package com.lonedev.favouriteapp.di.components

import android.app.Application
import com.lonedev.favouriteapp.base.BaseApplication
import com.lonedev.favouriteapp.di.modules.AppModule
import com.lonedev.favouriteapp.di.modules.NetworkModule
import com.lonedev.favouriteapp.di.modules.ViewModelFactoryModule
import com.lonedev.favouriteapp.di.builders.ActivityBuildersModule
import com.lonedev.favouriteapp.di.builders.FragmentBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AppModule::class),
        (NetworkModule::class),
        (ActivityBuildersModule::class),
        (FragmentBuildersModule::class),
        (ViewModelFactoryModule::class),
        (AndroidSupportInjectionModule::class)
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}