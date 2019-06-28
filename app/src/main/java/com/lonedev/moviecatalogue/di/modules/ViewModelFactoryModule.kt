/*
 * Created by Bagus Yogatama on 6/26/19 8:29 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/26/19 8:29 PM
 */

package com.lonedev.moviecatalogue.di.modules

import androidx.lifecycle.ViewModelProvider
import com.lonedev.moviecatalogue.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(providerFactory: ViewModelFactory): ViewModelProvider.Factory

}