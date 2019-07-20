/*
 * Created by Bagus Yogatama on 7/20/19 8:23 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/18/19 1:19 AM
 */

package com.lonedev.favouriteapp.di.builders

import com.lonedev.favouriteapp.ui.details.movie.MovieDetailActivity
import com.lonedev.favouriteapp.ui.details.tvseries.TVSeriesDetailActivity
import com.lonedev.favouriteapp.ui.favorite.FavoriteActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeTVSeriesDetailActivity(): TVSeriesDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoriteActivity(): FavoriteActivity

}