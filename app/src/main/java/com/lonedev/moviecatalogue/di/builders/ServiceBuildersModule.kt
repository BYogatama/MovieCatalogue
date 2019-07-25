/*
 * Created by Bagus Yogatama on 7/22/19 1:25 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/22/19 1:25 PM
 */

package com.lonedev.moviecatalogue.di.builders

import com.lonedev.moviecatalogue.service.MovieReleaseReminderReceiver
import com.lonedev.moviecatalogue.service.TVSeriesReleaseReminder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuildersModule {

    @ContributesAndroidInjector
    abstract fun provideMovieReleaseReminderReceiver(): MovieReleaseReminderReceiver

    @ContributesAndroidInjector
    abstract fun provideTVSeriesReleaseReminderReceiver() : TVSeriesReleaseReminder

}