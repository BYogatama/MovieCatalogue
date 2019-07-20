/*
 * Created by Bagus Yogatama on 7/20/19 8:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:36 PM
 */

package com.lonedev.favouriteapp.di.builders

import com.lonedev.favouriteapp.ui.favorite.fragment.movie.FavouritesMoviesFragment
import com.lonedev.favouriteapp.ui.favorite.fragment.tv.FavouritesTVFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeFavouritesMoviesFragment(): FavouritesMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeFavouritesTVFragment(): FavouritesTVFragment

}