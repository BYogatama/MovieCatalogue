package com.lonedev.moviecatalogue.di.builders

import com.lonedev.moviecatalogue.ui.main.MainActivity
import com.lonedev.moviecatalogue.ui.main.details.MovieDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): MovieDetailActivity

}