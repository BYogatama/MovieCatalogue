/*
 * Created by Bagus Yogatama on 7/20/19 8:21 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/2/19 4:34 PM
 */

package com.lonedev.favouriteapp.di.modules

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.lonedev.favouriteapp.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions {
        return RequestOptions
            .placeholderOf(R.drawable.image_placeholder)
            .error(R.drawable.image_placeholder)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application, requestOptions: RequestOptions): RequestManager {
        return Glide.with(application)
            .setDefaultRequestOptions(requestOptions)
    }

    @Singleton
    @Provides
    fun provideContentResolver(application: Application) : ContentResolver{
        return application.contentResolver
    }

}