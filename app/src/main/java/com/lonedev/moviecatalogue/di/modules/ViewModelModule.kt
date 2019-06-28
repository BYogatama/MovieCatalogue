/*
 * Created by Bagus Yogatama on 6/28/19 7:19 AM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/28/19 7:19 AM
 */

package com.lonedev.moviecatalogue.di.modules

import androidx.lifecycle.ViewModel
import com.lonedev.moviecatalogue.ui.main.fragment.movie.MovieViewModel
import com.lonedev.moviecatalogue.ui.main.fragment.tv.TVSeriesViewModel
import com.schoters.androidapp.di.util.ViewModelKey
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
}
