package com.lonedev.moviecatalogue.di.components

import android.app.Application
import com.lonedev.moviecatalogue.base.BaseApplication
import com.lonedev.moviecatalogue.di.modules.AppModule
import com.lonedev.moviecatalogue.di.modules.NetworkModule
import com.lonedev.moviecatalogue.di.modules.ViewModelFactoryModule
import com.lonedev.moviecatalogue.di.builders.ActivityBuildersModule
import com.lonedev.moviecatalogue.di.builders.FragmentBuildersModule
import com.lonedev.moviecatalogue.di.builders.ServiceBuildersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (AppModule::class),
        (NetworkModule::class),
        (ActivityBuildersModule::class),
        (FragmentBuildersModule::class),
        (ViewModelFactoryModule::class),
        (ServiceBuildersModule::class),
        (AndroidSupportInjectionModule::class)
    ]
)
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}