package com.lonedev.moviecatalogue.di.builders

import com.lonedev.moviecatalogue.ui.main.MainActivity
import com.lonedev.moviecatalogue.ui.main.details.MovieDetailActivity
import com.lonedev.moviecatalogue.ui.main.details.TVSeriesDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMovieDetailActivity(): MovieDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeTVSeriesDetailActivity(): TVSeriesDetailActivity

}