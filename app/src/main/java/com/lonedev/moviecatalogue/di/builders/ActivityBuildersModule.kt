package com.lonedev.moviecatalogue.di.builders

import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailActivity
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailActivity
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.FavoriteActivity
import com.lonedev.moviecatalogue.ui.main.main.MainActivity
import com.lonedev.moviecatalogue.ui.main.settings.SettingsActivity
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

    @ContributesAndroidInjector
    abstract fun contributeFavoriteActivity(): FavoriteActivity

    @ContributesAndroidInjector
    abstract fun contributeSettingsActivity(): SettingsActivity

}