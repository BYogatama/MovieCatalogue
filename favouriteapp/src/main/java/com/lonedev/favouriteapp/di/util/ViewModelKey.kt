/*
 * Created by Bagus Yogatama on 7/20/19 8:21 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 6/27/19 1:36 PM
 */

package com.lonedev.favouriteapp.di.util

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)