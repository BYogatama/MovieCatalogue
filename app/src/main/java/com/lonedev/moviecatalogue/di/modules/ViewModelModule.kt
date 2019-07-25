/*
 * Created by Bagus Yogatama on 6/28/19 7:19 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 7:19 AM
 */

package com.lonedev.moviecatalogue.di.modules

import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.di.util.ViewModelKey
import com.lonedev.moviecatalogue.ui.main.details.movie.MovieDetailViewModel
import com.lonedev.moviecatalogue.ui.main.details.tvseries.TVSeriesDetailViewModel
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.movie.FavouritesMoviesViewModel
import com.lonedev.moviecatalogue.ui.main.favorite.fragment.tv.FavouritesTVViewModel
import com.lonedev.moviecatalogue.ui.main.main.fragment.movie.MovieViewModel
import com.lonedev.moviecatalogue.ui.main.main.fragment.search.SearchViewModel
import com.lonedev.moviecatalogue.ui.main.main.fragment.tv.TVSeriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    abstract fun bindMovieViewModel(movieViewModel: MovieViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TVSeriesViewModel::class)
    abstract fun bindTVSeriesViewModel(tvSeriesViewModel: TVSeriesViewModel): ViewModel

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

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}
