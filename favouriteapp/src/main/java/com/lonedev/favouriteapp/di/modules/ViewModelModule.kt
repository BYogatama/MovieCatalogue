/*
 * Created by Bagus Yogatama on 7/20/19 8:21 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/20/19 7:36 PM
 */

package com.lonedev.favouriteapp.di.modules

import androidx.lifecycle.ViewModel
import com.lonedev.favouriteapp.di.util.ViewModelKey
import com.lonedev.favouriteapp.ui.details.movie.MovieDetailViewModel
import com.lonedev.favouriteapp.ui.details.tvseries.TVSeriesDetailViewModel
import com.lonedev.favouriteapp.ui.favorite.fragment.movie.FavouritesMoviesViewModel
import com.lonedev.favouriteapp.ui.favorite.fragment.tv.FavouritesTVViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TVSeriesDetailViewModel::class)
    abstract fun bindTVSeriesDetailViewModel(tvSeriesDetailViewModel: TVSeriesDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesMoviesViewModel::class)
    abstract fun bindFavouritesMoviesViewModel(favouritesMoviesViewModel: FavouritesMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouritesTVViewModel::class)
    abstract fun bindFavouritesTVViewModel(favouritesTVViewModel: FavouritesTVViewModel): ViewModel
}
