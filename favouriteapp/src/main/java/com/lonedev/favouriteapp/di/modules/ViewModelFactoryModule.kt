/*
 * Created by Bagus Yogatama on 7/20/19 8:22 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/26/19 8:31 PM
 */

package com.lonedev.favouriteapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.lonedev.favouriteapp.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(providerFactory: ViewModelFactory): ViewModelProvider.Factory

}