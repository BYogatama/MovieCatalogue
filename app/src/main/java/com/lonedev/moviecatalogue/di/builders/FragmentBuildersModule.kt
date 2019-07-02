package com.lonedev.moviecatalogue.di.builders

import com.lonedev.moviecatalogue.ui.main.favorite.fragment.movie.FavouritesMoviesFragment
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.tv.FavouritesTVFragment
import com.lonedev.moviecatalogue.ui.main.main.fragment.movie.MovieFragment
import com.lonedev.moviecatalogue.ui.main.main.fragment.tv.TVSeriesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMovieFragment(): MovieFragment

    @ContributesAndroidInjector
    abstract fun contributeTVSeriesFragment(): TVSeriesFragment

    @ContributesAndroidInjector
    abstract fun contributeFavouritesMoviesFragment(): FavouritesMoviesFragment

    @ContributesAndroidInjector
    abstract fun contributeFavouritesTVFragment(): FavouritesTVFragment

}