package com.lonedev.moviecatalogue.di.builders

import com.lonedev.moviecatalogue.ui.main.fragment.movie.MovieFragment
import com.lonedev.moviecatalogue.ui.main.fragment.tv.TVSeriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment
    @ContributesAndroidInjector
    abstract fun contributeTVSeriesFragment(): TVSeriesFragment


}