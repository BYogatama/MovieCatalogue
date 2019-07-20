/*
 * Created by Bagus Yogatama on 7/20/19 7:51 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 10:49 PM
 */

package com.lonedev.favouriteapp.base

import com.lonedev.favouriteapp.di.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Here is where the Dependency injection is happening One method execute all.
 */
class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}