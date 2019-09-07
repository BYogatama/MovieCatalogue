package com.lonedev.moviecatalogue.di.modules

import android.app.Application
import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.data.local.dao.MoviesDao
import com.lonedev.moviecatalogue.data.remote.MovieApi
import com.lonedev.moviecatalogue.data.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelModule::class, DatabaseModule::class])
object AppModule {

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

}